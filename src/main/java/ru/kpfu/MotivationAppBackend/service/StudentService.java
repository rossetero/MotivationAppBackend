package ru.kpfu.MotivationAppBackend.service;

import org.springframework.data.util.Pair;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentGoalsDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.List;

public interface StudentService {
    List<StudentTaskInfoDTO> getStudentTaskList(Long studentId);
    List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform);
    Pair<Double,Integer> addTask(AddTaskDTO addTaskDTO, Long studentId);
    StudentProfileDTO getStudentProfile(Long studentId);
    void editStudentProfile(StudentProfileDTO studentProfileDTO, Long studentId);
    List<StudentGoalsDTO> getParticipatedGroups(Long studentId);
    List<Pair<Double,Integer>> syncWithCodeforces(Long studentId);
    void deleteTask(AddTaskDTO addTaskDTO, Long studentId);
    List<Pair<Double, Integer>> syncWithAcmp(Long userId);
}
