package ru.kpfu.MotivationAppBackend.dto.cf;

import lombok.Data;


@Data
public class SubmissionDTO {
    private Long id;
    private Integer contestId;
    private Long creationTimeSeconds;
    private Long relativeTimeSeconds;
    private ProblemDTO problem;
    private AuthorDTO author;
    private String programmingLanguage;
    private String verdict;
    private String testset;
    private Integer passedTestCount;
    private Integer timeConsumedMillis;
    private Long memoryConsumedBytes;

    // геттеры и сеттеры
}



