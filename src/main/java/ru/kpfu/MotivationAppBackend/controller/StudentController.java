package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentGoalsDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;

import java.util.List;

public interface StudentController {
    @GetMapping("/tasks")
    List<StudentTaskInfoDTO> getStudentTaskList(@PathVariable Long userId);

    @GetMapping("/tasks/{platform:leetcode|acmp|codeforces}")
    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(@PathVariable Long userId, @PathVariable String platform);

    @PutMapping("/tasks/addTask")
    ResponseEntity<Pair<Double,Integer>> addTask(@RequestBody @Valid AddTaskDTO addTaskDTO, @PathVariable Long userId);

    @GetMapping("/profile")
    StudentProfileDTO getStudentProfile(@PathVariable Long userId);

    @GetMapping("/groups")
    List<StudentGoalsDTO> getParticipatedGroups(@PathVariable Long userId);

    @PutMapping("/profile/edit")
    ResponseEntity<String> editStudentProfile(@RequestBody @Valid StudentProfileDTO studentProfileDTO, @PathVariable Long userId);
}
