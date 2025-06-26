package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.cf.ResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.cf.SubmissionDTO;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CodeForcesServiceImpl {


    private final RestTemplate restTemplate;

    @Autowired
    public CodeForcesServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private List<SubmissionDTO> getStudentCfSubmissions(String handle) {
        String url = "https://codeforces.com/api/user.status?lang=ru&handle=" + handle;
        ResponseDTO response = restTemplate.getForObject(url, ResponseDTO.class);
        if (response != null && "OK".equalsIgnoreCase(response.getStatus())) {
            return response.getResult();
        }
        throw new RuntimeException("Codeforces API returned error or empty response");
    }

    public List<AddTaskDTO> getTaskFromSubmissions(String handle) {
        List<AddTaskDTO> l = getStudentCfSubmissions(handle).stream()
                .map(this::mapperSubmissionTask)
                .collect(Collectors.toMap(AddTaskDTO::getLink, t -> t, (e, r) -> e, LinkedHashMap::new))
                .values().stream()
                .toList();
        return l;
    }

    private AddTaskDTO mapperSubmissionTask(SubmissionDTO submissionDTO) {
        AddTaskDTO mappedTask = new AddTaskDTO();
        mappedTask.setPlatform(Platform.CODEFORCES);
        mappedTask.setNumber(submissionDTO.getProblem().getContestId() + submissionDTO.getProblem().getIndex());
        mappedTask.setTitle(submissionDTO.getProblem().getName());
        if(submissionDTO.getProblem().getRating()!=null)
            mappedTask.setDifficulty(submissionDTO.getProblem().getRating());
        else
            mappedTask.setDifficulty(800);
        String link = "https://codeforces.com/problemset/problem/" +
                +submissionDTO.getProblem().getContestId() + "/" + submissionDTO.getProblem().getIndex();
        mappedTask.setLink(link);
        String strVerdict = submissionDTO.getVerdict();
        Verdict verdict = strVerdict.equals("OK") ? Verdict.SUCCESS : Verdict.FAIL;
        mappedTask.setVerdict(verdict);
        return mappedTask;
    }

}
