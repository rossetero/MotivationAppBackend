package ru.kpfu.MotivationAppBackend.dto;

import jakarta.persistence.Column;
import jdk.jshell.Snippet;
import lombok.*;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentTaskInfoDTO {
    private Long studentId;
    private Platform platform;
    private String title;
    private String link;
    private Verdict verdict;
}
