package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.service.RegistrationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final RegistrationService registrationService;
    @Autowired
    public AuthController(RegistrationService registrationService){
        this.registrationService=registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO request) {
        registrationService.registerStudent(request);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }
}
