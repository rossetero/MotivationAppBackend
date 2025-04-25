package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;

import java.util.List;

public interface StudentController {
    @GetMapping("/tasks")
    List<StudentTaskInfoDTO> getStudentTaskList(@PathVariable Long userId);

    @GetMapping("/tasks/{platform:leetcode|acmp|codeforces}")
    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(@PathVariable Long userId, @PathVariable String platform);

    @PutMapping("/tasks/addTask")
    ResponseEntity<String> addTask(@RequestBody @Valid AddTaskDTO addTaskDTO, @PathVariable Long userId);

    @GetMapping("/profile")
    StudentProfileDTO getStudentProfile(@PathVariable Long userId);

    @PutMapping("/profile/edit")
    ResponseEntity<String> editStudentProfile(@RequestBody @Valid StudentProfileDTO studentProfileDTO, @PathVariable Long userId);
}
