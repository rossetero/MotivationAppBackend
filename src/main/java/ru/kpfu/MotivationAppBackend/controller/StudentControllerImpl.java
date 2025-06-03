package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentGoalsDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.service.StudentService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/v1/users/{userId}/student")
public class StudentControllerImpl implements StudentController {
    StudentService studentService;

    @Autowired
    public StudentControllerImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/tasks")
    @Override
    public List<StudentTaskInfoDTO> getStudentTaskList(@PathVariable Long userId) {
        return studentService.getStudentTaskList(userId);
    }

    @GetMapping("/tasks/{platform:leetcode|acmp|codeforces}")
    @Override
    public List<StudentTaskInfoDTO> getStudentTaskListByPlatform(@PathVariable Long userId, @PathVariable String platform) {
        return studentService.getStudentTaskListByPlatform(userId, Platform.valueOf(platform.toUpperCase()));
    }

    @PutMapping("/tasks/addTask")
    @Override
    public ResponseEntity<Pair<Double,Integer>> addTask(@RequestBody @Valid AddTaskDTO addTaskDTO, @PathVariable Long userId) {
        Pair<Double,Integer> diffAndScore = studentService.addTask(addTaskDTO, userId);
        return ResponseEntity.ok(diffAndScore);
    }

    @Override
    public ResponseEntity<String> deleteTask(@RequestBody @Valid AddTaskDTO addTaskDTO, @PathVariable Long userId) {
        studentService.deleteTask(addTaskDTO,userId);
        return ResponseEntity.ok("Task deleted");
    }

    @GetMapping("/profile")
    @Override
    public StudentProfileDTO getStudentProfile(@PathVariable Long userId) {
        return studentService.getStudentProfile(userId);
    }
    @GetMapping("/groups")
    @Override
    public List<StudentGoalsDTO> getParticipatedGroups(Long userId) {
        return studentService.getParticipatedGroups(userId);
    }

    @PutMapping("/profile/edit")
    @Override
    public ResponseEntity<String> editStudentProfile(@RequestBody @Valid StudentProfileDTO studentProfileDTO, @PathVariable Long userId) {
        studentService.editStudentProfile(studentProfileDTO, userId);
        return ResponseEntity.ok("Profile Updated");
    }

    @Override
    public ResponseEntity<List<Pair<Double, Integer>>> syncWithCodeforces(Long userId) {
        List<Pair<Double, Integer>> diffAndScoreList = studentService.syncWithCodeforces(userId);
        return ResponseEntity.ok(diffAndScoreList);
    }

    @Override
    public void syncWithAcmp(Long userId) {
        studentService.syncWithAcmp(userId);
    }


}
