package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;

import java.util.List;

public interface StudentController {
    List<StudentTaskInfoDTO> getStudentTaskList(@PathVariable Long userId);

    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(@PathVariable Long userId, String platform);

    ResponseEntity<String> addTask(@RequestBody @Valid AddTaskDTO addTaskDTO, @PathVariable Long userId);

    StudentProfileDTO getStudentProfile(@PathVariable Long userId);

    ResponseEntity<String> editStudentProfile(@RequestBody @Valid StudentProfileDTO studentProfileDTO, @PathVariable Long userId);
}
