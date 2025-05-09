package ru.kpfu.MotivationAppBackend.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.entity.Teacher;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    boolean existsByLogin(String login);
    Optional<Teacher> findByLogin(@NotBlank @Size(min = 3, max = 50) String login);
}


