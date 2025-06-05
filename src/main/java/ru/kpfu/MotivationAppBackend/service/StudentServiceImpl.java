package ru.kpfu.MotivationAppBackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.*;
import ru.kpfu.MotivationAppBackend.entity.*;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.enums.Verdict;
import ru.kpfu.MotivationAppBackend.repository.StudentGroupRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentTaskRepository;
import ru.kpfu.MotivationAppBackend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentTaskRepository studentTaskRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final CodeForcesServiceImpl codeForcesService;
    private final AcmpServiceImpl acmpService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentTaskRepository studentTaskRepository, StudentGroupRepository studentGroupRepository, TaskService taskService, TaskRepository taskRepository, CodeForcesServiceImpl codeForcesService, AcmpServiceImpl acmpService) {
        this.studentRepository = studentRepository;
        this.studentTaskRepository = studentTaskRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.codeForcesService = codeForcesService;
        this.acmpService = acmpService;
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskList(Long studentId) {
        return studentTaskRepository.findAllStudentTaskInfoByStudentId(studentId);
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform) {
        return studentTaskRepository.findStudentTaskInfoByStudentIdAndPlatform(studentId, platform);
    }

    private int getTaskScore(double normalizedDiff, Group g) {
        double easyMediumThreshold = g.getEasyMediumThreshold();
        double mediumThreshold = g.getMediumHardThreshold();
        if (normalizedDiff <= easyMediumThreshold) {
            System.out.println("=====EASY======");
            return 1;
        } else if (normalizedDiff < mediumThreshold) {
            System.out.println("====MEDIUM=====");
            return 2;
        } else {
            System.out.println("=====HARD====");
            return 3;
        }

    }

    private Pair<Double, Integer> incrementCurrentScore(AddTaskDTO addTaskDTO, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        StudentGroup sg = student.getParticipatedGroups().getFirst();
        Group g = sg.getGroup();
        double normalizedDiff = taskService.normalizeDiff(addTaskDTO.getDifficulty(), addTaskDTO.getPlatform());
        int taskScore = 0;
        if (normalizedDiff >= g.getMinAvgDifficulty() && addTaskDTO.getVerdict() == Verdict.SUCCESS) {
            taskScore = getTaskScore(normalizedDiff, g);
            int score = sg.getStudentCurrentScore() + taskScore;
            sg.setStudentCurrentScore(score);
            studentGroupRepository.save(sg);
        }
        return Pair.of(normalizedDiff, taskScore);
    }

    @Override
    public void deleteTask(AddTaskDTO addTaskDTO, Long studentId) {
        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
        StudentTaskInfoDTO relation = studentTaskRepository.
                findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform(),
                        addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(() -> new EntityNotFoundException("Task for student to delete not found"));
        StudentTask studentTask = studentTaskRepository.findById(relation.getId()).orElseThrow(RuntimeException::new);
        Task taskToDelete = studentTask.getTask();
        int currentStudentScore = studentTask.getStudent().getParticipatedGroups().getFirst().getStudentCurrentScore();
        StudentGroup sg = studentTask.getStudent().getParticipatedGroups().getFirst();
        int newCurrentStudentScore = currentStudentScore - getTaskScore(taskToDelete.getDifficulty(), sg.getGroup());
        sg.setStudentCurrentScore(newCurrentStudentScore);
        studentGroupRepository.save(sg);
        studentTaskRepository.delete(studentTask);
        taskRepository.delete(taskToDelete);
    }


    @Override
    public Pair<Double, Integer> addTask(AddTaskDTO addTaskDTO, Long studentId) {
        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
        Optional<StudentTaskInfoDTO> relation = studentTaskRepository.
                findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform(),
                        addTaskDTO.getTitle(), addTaskDTO.getLink());
        Pair<Double, Integer> taskDiffScore = null;
        if (relation.isEmpty()) {
            taskService.addTaskIfNotExists(addTaskDTO);
            Task task = taskService.findByTitleAndLink(addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(RuntimeException::new);
            StudentTask studentTask = new StudentTask();
            studentTask.setStudent(studentRepository.findById(studentId).orElseThrow(RuntimeException::new));
            studentTask.setTask(task);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTask.setLastChangedTime(LocalDateTime.now());
            studentTaskRepository.save(studentTask);
            taskDiffScore = incrementCurrentScore(addTaskDTO, studentId);
        } else if (relation.get().getVerdict() != addTaskDTO.getVerdict()) {
            StudentTask studentTask = studentTaskRepository.findById(relation.get().getId()).orElseThrow(RuntimeException::new);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTask.setLastChangedTime(LocalDateTime.now());
            studentTaskRepository.save(studentTask);
            taskDiffScore = incrementCurrentScore(addTaskDTO, studentId);
        } else {
            System.out.println("No new content");
        }
        return taskDiffScore;
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
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return student.getParticipatedGroups().stream().map(
                studentGroup -> {
                    Group g = studentGroup.getGroup();
                    return new StudentGoalsDTO(
                            g.getId(),
                            g.getName(),
                            g.getGroupGoal(),
                            studentGroup.getStudentGoal(),
                            studentGroup.getStudentCurrentScore(),
                            g.getEasyMediumThreshold(),
                            g.getMediumHardThreshold()
                    );
                }
        ).toList();

    }

    @Override
    public List<Pair<Double, Integer>> syncWithCodeforces(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        if (student.getCfHandler() == null)
            throw new RuntimeException("CF HANDLER IS NULL");
        List<AddTaskDTO> tasksFromCF = codeForcesService.getTaskFromSubmissions(student.getCfHandler())
                .reversed();
        System.out.println("========== TasksFROM CF==========");
        System.out.println(tasksFromCF);
        System.out.println("========== TasksFROM CF==========");
        List<Pair<Double, Integer>> diffAndScoreList = new ArrayList<>();
        for (AddTaskDTO taskDTO : tasksFromCF) {
            Pair<Double, Integer> taskDiffScore = addTask(taskDTO, studentId);
            if(taskDiffScore!=null)
                diffAndScoreList.add(taskDiffScore);
        }
        return diffAndScoreList;
    }


    @Override
    public List<Pair<Double, Integer>> syncWithAcmp(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        String acmpId = student.getAcmpId();
        if (acmpId == null)
            throw new RuntimeException("ACMP ID IS NULL");
        try {
            Integer.parseInt(acmpId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("WRONG ACMP ID FORMAT");
        }
        List<AddTaskDTO> tasksFromAcmp = acmpService.getTaskFromAcmpInDTO(acmpId);
        List<Pair<Double, Integer>> diffAndScoreList = new ArrayList<>();
        for (AddTaskDTO taskDTO : tasksFromAcmp) {
            Pair<Double, Integer> taskDiffScore = addTask(taskDTO, studentId);
            if(taskDiffScore!=null)
                diffAndScoreList.add(taskDiffScore);
        }
        return diffAndScoreList;
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
