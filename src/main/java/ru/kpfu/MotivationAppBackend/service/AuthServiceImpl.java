package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.LoginRequestDTO;
import ru.kpfu.MotivationAppBackend.dto.LoginResponseDTO;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.entity.User;
import ru.kpfu.MotivationAppBackend.repository.UserRepository;
import ru.kpfu.MotivationAppBackend.utils.UserFactory;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(UserRepository userRepository
                           //,PasswordEncoder passwordEncoder
    ){
        this.userRepository=userRepository;
       // this.passwordEncoder=passwordEncoder;
    }

    @Override
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

    @Override
    public void register(RegistrationRequestDTO request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("This login already exists");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Wrong password");
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
