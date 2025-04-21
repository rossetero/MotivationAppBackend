package ru.kpfu.MotivationAppBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kpfu.MotivationAppBackend.entity.Teacher;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherRepositoryTest {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnTeachersGroups(){
        Teacher t = teacherRepository.findById(2L).orElseThrow(RuntimeException::new);
        System.out.println(t.getOwnedGroups());
    }
}