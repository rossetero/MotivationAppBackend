package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.repository.TaskRepository;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    TaskRepository taskRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    @Override
    public void addTaskIfNotExists(AddTaskDTO addTaskDTO) {
        if (!(taskRepository.existsByTitle(addTaskDTO.getTitle()) && taskRepository.existsByLink(addTaskDTO.getLink()))){
            Task newTask = new Task();
            newTask.setPlatform(addTaskDTO.getPlatform());
            newTask.setTitle(addTaskDTO.getTitle());
            newTask.setLink(addTaskDTO.getLink());
            taskRepository.save(newTask);
            System.out.println("New Task Saved");
        } else {
            System.out.println("Task Exists");
        }
    }

    @Override
    public Optional<Task> findByTitleAndLink(String title, String link) {
        return taskRepository.findByTitleAndLink(title,link);
    }


}
