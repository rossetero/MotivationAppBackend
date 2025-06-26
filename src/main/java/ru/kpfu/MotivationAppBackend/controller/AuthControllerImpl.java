package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.service.AuthService;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    @Override
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }
    @PostMapping("/login")
    @Override
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO loginResponseDTO = authService.login(request);
        return ResponseEntity.ok(loginResponseDTO);
    }
}


