package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.entity.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitle(String title);
    boolean existsByLink(String link);
    Optional<Task> findByTitleAndLink(String title, String link);
}
