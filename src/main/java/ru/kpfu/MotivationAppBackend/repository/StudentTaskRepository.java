package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Long> {
}
