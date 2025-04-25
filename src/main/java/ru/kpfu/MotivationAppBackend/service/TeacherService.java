package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;

public interface TeacherService {
    TeacherProfileDTO getTeacherProfile(Long teacherId);

    void editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long teacherId);
}
