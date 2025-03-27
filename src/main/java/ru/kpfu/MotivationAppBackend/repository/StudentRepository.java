package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByLogin(String login);
}

