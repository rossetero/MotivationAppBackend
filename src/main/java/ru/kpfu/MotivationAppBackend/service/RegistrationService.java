package ru.kpfu.MotivationAppBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.RegistrationRequestDTO;
import ru.kpfu.MotivationAppBackend.enums.Role;
import ru.kpfu.MotivationAppBackend.repository.StudentRepository;
import ru.kpfu.MotivationAppBackend.entity.Student;

@Service
public class RegistrationService {
    private final StudentRepository studentRepository;
    //private final PasswordEncoder passwordEncoder;
    @Autowired
    public RegistrationService(StudentRepository studentRepository
            //,PasswordEncoder passwordEncoder
    ){
        this.studentRepository=studentRepository;
       // this.passwordEncoder=passwordEncoder;
    }

    public void registerStudent(RegistrationRequestDTO request) {
        if (studentRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }


        switch (request.getRole()){
            case STUDENT:
                Student student = new Student();
                student.setLogin(request.getLogin());
                student.setName(request.getName());
                //student.setPassword(passwordEncoder.encode(request.getPassword()));
                student.setPassword(request.getPassword());
                student.setRole(Role.STUDENT);
                studentRepository.save(student);
                break;
            case TEACHER:
                System.out.println("NOT IMPLEMENTED");
                break;
        }

    }
}
