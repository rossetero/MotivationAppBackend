package ru.kpfu.MotivationAppBackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jshell.Snippet;
import lombok.*;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentTaskInfoDTO {
    @JsonIgnore
    private Long id = null;
    @NotBlank
    private Platform platform;
    @NotBlank
    private String number;
    @NotBlank
    private String title;
    private double difficulty;
    @NotBlank
    private String link;
    @NotBlank
    private Verdict verdict;
    private LocalDateTime lastChangedTime;

    public StudentTaskInfoDTO(Platform platform,String number, String title,double difficulty, String link, Verdict verdict,LocalDateTime lastChangedTime) {
        this.platform = platform;
        this.number = number;
        this.title = title;
        this.difficulty = difficulty;
        this.link = link;
        this.verdict = verdict;
        this.lastChangedTime=lastChangedTime;
    }
}
