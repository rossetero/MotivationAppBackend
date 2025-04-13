package ru.kpfu.MotivationAppBackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @JsonIgnore
    private Long id = null;
    @NotBlank
    private Platform platform;
    @NotBlank
    private String title;
    @NotBlank
    private String link;
    @NotBlank
    private Verdict verdict;

    public StudentTaskInfoDTO(Platform platform, String title, String link, Verdict verdict) {
        this.platform = platform;
        this.title = title;
        this.link = link;
        this.verdict = verdict;
    }
}
