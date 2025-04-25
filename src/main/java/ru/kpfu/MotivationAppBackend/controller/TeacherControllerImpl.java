package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;
import ru.kpfu.MotivationAppBackend.service.TeacherService;

@RestController
@RequestMapping("/api/v1/users/{userId}/teacher")
public class TeacherControllerImpl implements TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherControllerImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/profile")
    @Override
    public TeacherProfileDTO getTeacherProfile(@PathVariable Long userId) {
        return teacherService.getTeacherProfile(userId);
    }

    @PutMapping("/profile/edit")
    @Override
    public ResponseEntity<String> editTeacherProfile(@RequestBody @Valid TeacherProfileDTO teacherProfileDTO, @PathVariable Long userId) {
        teacherService.editTeacherProfile(teacherProfileDTO, userId);
        return ResponseEntity.ok("Profile Updated");
    }

}