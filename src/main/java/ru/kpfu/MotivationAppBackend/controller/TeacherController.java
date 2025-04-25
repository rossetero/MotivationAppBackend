package ru.kpfu.MotivationAppBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;

public interface TeacherController {
    @GetMapping("/profile")
    TeacherProfileDTO getTeacherProfile(Long userId);

    @PutMapping("/profile/edit")
    ResponseEntity<String> editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long userId);
}
