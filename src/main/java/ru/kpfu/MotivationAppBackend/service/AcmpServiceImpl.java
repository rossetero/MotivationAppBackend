package ru.kpfu.MotivationAppBackend.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AcmpServiceImpl {
    private final RestTemplate restTemplate;


    @ToString
    private class AcmpData {
        private final List<String> successfulTasksLinks;
        private final List<String> failedTasksLinks;
        private List<Integer> successfulTasksDifficulty;
        private List<Integer> failedTasksDifficulty;

        public AcmpData(List<String> successfulTasksLinks, List<String> failedTasksLinks) {
            this.successfulTasksLinks = successfulTasksLinks;
            this.failedTasksLinks = failedTasksLinks;
        }
    }

    @Autowired
    public AcmpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<AddTaskDTO> mapToAddTaskDTO(AcmpData data) {
        List<AddTaskDTO> syncedAcmpTasks = new ArrayList<>(
                data.successfulTasksDifficulty.size() +
                        data.failedTasksDifficulty.size());
        for (int i = 0; i < data.successfulTasksLinks.size(); i++) {
            AddTaskDTO addTaskDTO = new AddTaskDTO();
            addTaskDTO.setPlatform(Platform.ACMP);
            String url = data.successfulTasksLinks.get(i);
            String number = url.substring(url.lastIndexOf("=") + 1);
            addTaskDTO.setNumber(number);
            addTaskDTO.setTitle("Задача №" + number);
            addTaskDTO.setDifficulty(data.successfulTasksDifficulty.get(i));
            addTaskDTO.setLink(url);
            addTaskDTO.setVerdict(Verdict.SUCCESS);
            syncedAcmpTasks.add(addTaskDTO);
        }
        for (int i = 0; i < data.failedTasksLinks.size(); i++) {
            AddTaskDTO addTaskDTO = new AddTaskDTO();
            addTaskDTO.setPlatform(Platform.ACMP);
            String url = data.failedTasksLinks.get(i);
            String number = url.substring(url.lastIndexOf("=") + 1);
            addTaskDTO.setNumber(number);
            addTaskDTO.setTitle("Задача №" + number);
            addTaskDTO.setDifficulty(data.failedTasksDifficulty.get(i));
            addTaskDTO.setLink(url);
            addTaskDTO.setVerdict(Verdict.FAIL);
            syncedAcmpTasks.add(addTaskDTO);
        }
        return syncedAcmpTasks;
    }

    private Integer extractDifficultyFromTaskPageHtml(String url) {
        String response = restTemplate.getForObject(url, String.class);
        if (response != null) {
            String difficultyRegex = "\\(\\S+ \\S+ \\S+ \\S+ \\S+ (\\d+)%\\)";
            Pattern pattern = Pattern.compile(difficultyRegex);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1));
            }
        }
        throw new RuntimeException("ACMP API returned error or empty response");
    }

    private AcmpData getTaskDifficultiesData(AcmpData data) {
        data.failedTasksDifficulty = data.failedTasksLinks.stream()
                .map(this::extractDifficultyFromTaskPageHtml).toList();
        data.successfulTasksDifficulty = data.successfulTasksLinks.stream()
                .map(this::extractDifficultyFromTaskPageHtml).toList();
        return data;
    }



    //"\((\d+)\):<\/b>"g - количество задач в группе на старнце (решенные и нерешенные)
    //c группой захвата на число количесвта
    //\?main=task&id_task=\d+ - получить ссылки на задачи и добавить к ним acmp.ru
    //\(\S+ \S+ \S+ \S+ \S+ (\d+)%\) - сложность на станице с задачей и захватом на величину сложности

    private AcmpData extractDataFromUserPageRawHtml(String raw) {
        String taskAmountRegex = "\\((\\d+)\\):<\\/b>";
        Pattern pattern = Pattern.compile(taskAmountRegex);
        Matcher matcher = pattern.matcher(raw);
        int successfulTasksAmount = 0;
        if (matcher.find())
            successfulTasksAmount = Integer.parseInt(matcher.group(1));
        int failedTasksAmount = 0;
        if (matcher.find())
            failedTasksAmount = Integer.parseInt(matcher.group(1));
        String taskLinkRegex = "\\?main=task&id_task=\\d+";
        pattern = Pattern.compile(taskLinkRegex);
        matcher = pattern.matcher(raw);
        List<String> successfulTasksLinks = new ArrayList<>(successfulTasksAmount);
        for (int i = 0; i < successfulTasksAmount && matcher.find(); i++) {
            successfulTasksLinks.add("https://acmp.ru/" + matcher.group(0));
        }
        List<String> failedTasksLinks = new ArrayList<>(failedTasksAmount);
        for (int i = 0; i < failedTasksAmount && matcher.find(); i++) {
            failedTasksLinks.add("https://acmp.ru/" + matcher.group(0));
        }
        return new AcmpData(successfulTasksLinks, failedTasksLinks);
    }

    public String getRawHtmlUserPage(String acmpId) {
        String url = "https://acmp.ru/?main=user&id=" + acmpId;
        String response = restTemplate.getForObject(url, String.class);
        if (response != null) {
            return new String(response.getBytes(StandardCharsets.ISO_8859_1),
                    Charset.forName("Windows-1251"));
        }
        throw new RuntimeException("ACMP returned error or empty response");
    }



    public List<AddTaskDTO> getTaskFromAcmpInDTO(String acmpId){
        return mapToAddTaskDTO(
                getTaskDifficultiesData(
                        extractDataFromUserPageRawHtml(
                                getRawHtmlUserPage(acmpId))));
    }
//
//    public void syncWithAcmp(String acmpId) {
//        List<AddTaskDTO> taskFromAcmp = getTaskFromAcmpInDTO(acmpId);
//    }
}
