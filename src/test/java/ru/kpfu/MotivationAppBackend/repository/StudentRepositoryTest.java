package ru.kpfu.MotivationAppBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kpfu.MotivationAppBackend.entity.Student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @Test
    public void shouldReturnStudentsTasksAndVerdicts(){
        Student student = studentRepository.findById(1L).get();
        System.out.println(student.getStudentsTasks());
       //
    }
}
