package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;

public interface AuthController {
    ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO request);

    ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request);
}
