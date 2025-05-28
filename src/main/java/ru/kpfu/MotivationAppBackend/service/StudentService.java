package ru.kpfu.MotivationAppBackend.service;

import org.springframework.http.ResponseEntity;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentGoalsDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.List;

public interface StudentService {
    List<StudentTaskInfoDTO> getStudentTaskList(Long studentId);
    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform);
    int addTask(AddTaskDTO addTaskDTO, Long studentId);
    StudentProfileDTO getStudentProfile(Long studentId);
    void editStudentProfile(StudentProfileDTO studentProfileDTO, Long studentId);
    List<StudentGoalsDTO> getParticipatedGroups(Long studentId);
}
