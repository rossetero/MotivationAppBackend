package ru.kpfu.MotivationAppBackend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

@Getter
@Setter
public class AddTaskDTO {
    @NotNull
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @NotBlank
    private String title;
    @NotBlank
    private String link;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Verdict verdict;
}
