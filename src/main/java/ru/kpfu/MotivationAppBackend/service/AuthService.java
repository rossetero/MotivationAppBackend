package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    void register(RegistrationRequestDTO request);
}
