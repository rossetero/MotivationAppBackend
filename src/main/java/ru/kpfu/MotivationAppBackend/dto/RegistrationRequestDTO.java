package ru.kpfu.MotivationAppBackend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.kpfu.MotivationAppBackend.enums.Role;

@Data
public class RegistrationRequestDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String login;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    @Size(min = 6, max = 50,message = "password must me longer than 6")
    private String password;

    @NotBlank
    private String repeatPassword;
}
