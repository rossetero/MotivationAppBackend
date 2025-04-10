package ru.kpfu.MotivationAppBackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String login;
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
}
