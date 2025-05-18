package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.repository.TaskRepository;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private double normalizeDiff(double inputDiff, Platform platform) {
        if (platform == Platform.ACMP) {
            return inputDiff;
        } else {
            Pair<Double, Double> appDiffRange = Pair.of(1.0, 100.0);
            Pair<Double, Double> platformDiffRange = switch (platform) {
                case CODEFORCES -> Pair.of(800.0, 3500.0);
                case LEETCODE -> Pair.of(1.0, 3.0);
                default -> throw new IllegalStateException("Unexpected value: " + platform);
            };
            return Math.ceil(1 + (inputDiff - platformDiffRange.getFirst()) * 99 / (platformDiffRange.getSecond() - platformDiffRange.getFirst()));
        }
    }

    @Override
    public void addTaskIfNotExists(AddTaskDTO addTaskDTO) {
        if (!(taskRepository.existsByTitle(addTaskDTO.getTitle()) && taskRepository.existsByLink(addTaskDTO.getLink()))) {
            Task newTask = new Task();
            newTask.setPlatform(addTaskDTO.getPlatform());
            newTask.setTitle(addTaskDTO.getTitle());
            newTask.setDifficulty(normalizeDiff(addTaskDTO.getDifficulty(),addTaskDTO.getPlatform()));
            newTask.setLink(addTaskDTO.getLink());
            taskRepository.save(newTask);
            System.out.println("New Task Saved");
        } else {
            System.out.println("Task Exists");
        }
    }

    @Override
    public Optional<Task> findByTitleAndLink(String title, String link) {
        return taskRepository.findByTitleAndLink(title, link);
    }
}
