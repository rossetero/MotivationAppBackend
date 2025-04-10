package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request){
        LoginResponseDTO loginResponseDTO = authService.login(request);
        return ResponseEntity.ok(loginResponseDTO);
    }

}
