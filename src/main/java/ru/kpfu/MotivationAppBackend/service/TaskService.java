package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.Optional;

public interface TaskService {
    public double normalizeDiff(double inputDiff, Platform platform);
    void addTaskIfNotExists(AddTaskDTO addTaskDTO);
    Optional<Task> findByTitleAndLink(String title, String link);
}
