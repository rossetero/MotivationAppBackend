package ru.kpfu.MotivationAppBackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.*;
import ru.kpfu.MotivationAppBackend.entity.Group;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.repository.StudentRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentTaskRepository studentTaskRepository;
    private final TaskService taskService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentTaskRepository studentTaskRepository, TaskService taskService) {
        this.studentRepository = studentRepository;
        this.studentTaskRepository = studentTaskRepository;
        this.taskService = taskService;
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskList(Long studentId) {
        return studentTaskRepository.findAllStudentTaskInfoByStudentId(studentId);
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform) {
        return studentTaskRepository.findStudentTaskInfoByStudentIdAndPlatform(studentId, platform);
    }

    @Override
    public void addTask(AddTaskDTO addTaskDTO, Long studentId) {
        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
        Optional<StudentTaskInfoDTO> relation = studentTaskRepository.
                findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform(),
                        addTaskDTO.getTitle(), addTaskDTO.getLink());
        if (relation.isEmpty()) {
            taskService.addTaskIfNotExists(addTaskDTO);
            Task task = taskService.findByTitleAndLink(addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(RuntimeException::new);
            StudentTask studentTask = new StudentTask();
            studentTask.setStudent(studentRepository.findById(studentId).orElseThrow(RuntimeException::new));
            studentTask.setTask(task);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTask.setLastChangedTime(LocalDateTime.now());
            studentTaskRepository.save(studentTask);
        } else if (relation.get().getVerdict() != addTaskDTO.getVerdict()) {
            StudentTask studentTask = studentTaskRepository.findById(relation.get().getId()).orElseThrow(RuntimeException::new);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTask.setLastChangedTime(LocalDateTime.now());
            studentTaskRepository.save(studentTask);
        } else {
            System.out.println("No new content");
        }
    }

    @Override
    public StudentProfileDTO getStudentProfile(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(RuntimeException::new);
        return new StudentProfileDTO(student.getId(), student.getLogin(), student.getName(), student.getCfHandler(), student.getAcmpId());
    }

    @Override
    public void editStudentProfile(StudentProfileDTO studentProfileDTO, Long studentId) {
        Student s = studentRepository.findById(studentId).orElseThrow();
        s.setName(studentProfileDTO.getName());
        s.setCfHandler(studentProfileDTO.getCfHandler());
        s.setAcmpId(studentProfileDTO.getAcmpId());
        studentRepository.save(s);
    }

    @Override
    public List<StudentGoalsDTO> getParticipatedGroups(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return student.getParticipatedGroups().stream().map(
                studentGroup -> {
                    Group g = studentGroup.getGroup();
                    int goal = studentGroup.getStudentGoal();
                    return new StudentGoalsDTO(
                            g.getId(),
                            g.getName(),
                            g.getGroupGoal(),
                            studentGroup.getStudentGoal(),
                            studentGroup.getStudentCurrentScore()
                    );
                }
        ).toList();

    }

    private String removePrefix(String url) {
        if (url.startsWith("http://")) {
            return url.substring(7);
        } else if (url.startsWith("https://")) {
            return url.substring(8);
        }
        return url;
    }
}
