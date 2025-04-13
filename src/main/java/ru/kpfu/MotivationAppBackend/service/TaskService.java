package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.entity.Task;

import java.util.Optional;

public interface TaskService {
    void addTaskIfNotExists(AddTaskDTO addTaskDTO);
    Optional<Task> findByTitleAndLink(String title, String link);
}
