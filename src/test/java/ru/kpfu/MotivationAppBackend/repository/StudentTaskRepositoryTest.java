package ru.kpfu.MotivationAppBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentTaskRepositoryTest {
    @Autowired
    StudentTaskRepository studentTaskRepository;

    @Test
    public void shouldReturnStudentTaskInfoDTObyStudentId(){
        System.out.println(studentTaskRepository.findAllStudentTaskInfoByStudentId(3L));
    }

}
