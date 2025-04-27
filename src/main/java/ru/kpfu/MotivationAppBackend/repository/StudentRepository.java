package ru.kpfu.MotivationAppBackend.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByLogin(String login);
    Optional<Student> findByLogin(@NotBlank @Size(min = 3, max = 50) String login);
}

