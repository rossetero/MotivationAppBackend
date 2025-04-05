package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByLogin(String login);



}

