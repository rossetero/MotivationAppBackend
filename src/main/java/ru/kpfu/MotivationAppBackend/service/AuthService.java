package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.entity.User;
import ru.kpfu.MotivationAppBackend.enums.Role;
import ru.kpfu.MotivationAppBackend.repository.UserRepository;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.repository.UserRepository;
import ru.kpfu.MotivationAppBackend.utils.UserFactory;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(UserRepository userRepository
                       //,PasswordEncoder passwordEncoder
    ){
        this.userRepository=userRepository;
       // this.passwordEncoder=passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        Optional<User> user = userRepository.findByLogin(request.getLogin());
        if (user.isEmpty()){
            throw new RuntimeException("Нет такого пользователя");
        }
        if (!user.get().getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return new LoginResponseDTO(user.get().getId(), user.get().getRole().name());
    }

    public void register(RegistrationRequestDTO request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        User user = UserFactory.createUser(request.getRole());
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        userRepository.save(user);


    }
}
