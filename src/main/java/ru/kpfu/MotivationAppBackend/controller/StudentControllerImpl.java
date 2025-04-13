package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/student")
public class StudentControllerImpl implements StudentController {
    StudentService studentService;
    @Autowired
    public StudentControllerImpl(StudentService studentService){
        this.studentService=studentService;
    }

    @GetMapping("/tasks")
    @Override
    public List<StudentTaskInfoDTO> getStudentTaskList(@PathVariable Long userId){
        return  studentService.getStudentTaskList(userId);
    }

    @GetMapping("/tasks/{platform:leetcode|acmp|codeforces}")
    @Override
    public List<StudentTaskInfoDTO> getStudentTaskListByPlatform(@PathVariable Long userId, @PathVariable String platform){
        return  studentService.getStudentTaskListByPlatform(userId, Platform.valueOf(platform.toUpperCase()));
    }

    @PutMapping("/tasks/addTask")
    @Override
    public ResponseEntity<String> addTask(@RequestBody @Valid AddTaskDTO addTaskDTO,@PathVariable Long userId) {
        studentService.addTask(addTaskDTO,userId);
        return ResponseEntity.ok("Task Added");
    }


}
