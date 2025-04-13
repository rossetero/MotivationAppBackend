package ru.kpfu.MotivationAppBackend.service;

import org.springframework.http.ResponseEntity;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.List;

public interface StudentService {
    List<StudentTaskInfoDTO> getStudentTaskList(Long studentId);
    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform);
    void addTask(AddTaskDTO addTaskDTO, Long studentId);
}
