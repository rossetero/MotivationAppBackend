package ru.kpfu.MotivationAppBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kpfu.MotivationAppBackend.entity.Student;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnStudentsTasksAndVerdicts(){
        Student student = studentRepository.findById(3L).get();
        System.out.println(student.getStudentTaskList());
       //
    }

    @Test
    public void shouldAddAcmpId(){
    //  User user =  userRepository.findById(6L).get();
//        Student student = Student.builder().acmpId("535425").build();
//        student.setId(user.getId());
//        student.setRole(user.getRole());
//        student.setLogin(user.getLogin());
        Student student = (Student) userRepository.findById(3L).get();
        student.setAcmpId("535425");
        studentRepository.save(student);
        System.out.println(studentRepository.findAll());
        //fail();
        student = studentRepository.findById(3L).get();
        System.out.println(student.getAcmpId());

    }
}
