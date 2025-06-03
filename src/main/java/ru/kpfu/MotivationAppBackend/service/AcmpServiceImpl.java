package ru.kpfu.MotivationAppBackend.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AcmpServiceImpl {
    private final RestTemplate restTemplate;

    private record AcmpData( List<String> successfulTasksLinks,
                            List<String> failedTasksLinks) {
    }

    @Autowired
    public AcmpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AcmpData extractDataFromRawHtml(String raw) {
        //"\((\d+)\):<\/b>"g - количество задач в группе на старнце (решенные и нерешенные)
        //c группой захвата на число количесвта
        //\?main=task&id_task=\d+ - получить ссылки на задачи и добавить к ним acmp.ru
        //\(\S+ \S+ \S+ \S+ \S+ (\d+)%\) - сложность на станице с задачей и захватом на величину сложности
        String taskAmountRegex = "\\((\\d+)\\):<\\/b>";
        Pattern pattern = Pattern.compile(taskAmountRegex);
        Matcher matcher = pattern.matcher(raw);
        matcher.find();
        int successfulTasksAmount = Integer.parseInt(matcher.group(1));
        matcher.find();
        int failedTasksAmount = Integer.parseInt(matcher.group(1));
        String taskLinkRegex = "\\?main=task&id_task=\\d+";
        pattern = Pattern.compile(taskLinkRegex);
        matcher = pattern.matcher(raw);
        List<String> successfulTasksLinks = new ArrayList<>(successfulTasksAmount);
        for (int i = 0; i < successfulTasksAmount && matcher.find(); i++) {
            successfulTasksLinks.add("acmp.ru/" + matcher.group(0));
        }
        List<String> failedTasksLinks = new ArrayList<>(failedTasksAmount);
        for (int i = 0; i < failedTasksAmount && matcher.find(); i++) {
            failedTasksLinks.add("acmp.ru/" + matcher.group(0));
        }
        return new AcmpData(successfulTasksLinks, failedTasksLinks);
    }

    public String getRawHtmlUserPage(String acmpId) {
        String url = "https://acmp.ru/?main=user&id=" + acmpId;
        String response = restTemplate.getForObject(url, String.class);

        if (response != null) {
            return response;
        }

        throw new RuntimeException("Codeforces API returned error or empty response");
    }


    public void syncWithAcmp(String acmpId) {
        System.out.println(extractDataFromRawHtml(getRawHtmlUserPage(acmpId)));
    }
}


//@Getter
//@Setter
//@ToString
//public class AddTaskDTO {
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Platform platform;
//    @NotBlank
//    private String number;
//    @NotBlank
//    private String title;
//    @NotNull
//    private double difficulty;
//    @NotBlank
//    private String link;
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Verdict verdict;
//}

