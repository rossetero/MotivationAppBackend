package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.enums.Platform;
import ru.kpfu.MotivationAppBackend.repository.StudentRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentTaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{
    StudentRepository studentRepository;
    StudentTaskRepository studentTaskRepository;
    TaskService taskService;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,StudentTaskRepository studentTaskRepository,TaskService taskService){
        this.studentRepository=studentRepository;
        this.studentTaskRepository=studentTaskRepository;
        this.taskService=taskService;
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskList(Long studentId) {
        return studentTaskRepository.findAllStudentTaskInfoByStudentId(studentId);
    }

    @Override
    public List<StudentTaskInfoDTO> getStudentTaskListByPlatform(Long studentId, Platform platform) {
        return studentTaskRepository.findStudentTaskInfoByStudentIdAndPlatform(studentId,platform);
    }

//    @Override
//    public void addTask(AddTaskDTO addTaskDTO, Long studentId) {
//        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
//        taskService.addTaskIfNotExists(addTaskDTO);
//        Task task = taskService.findByTitleAndLink(addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(RuntimeException::new);
//        System.out.println(task);
//        StudentTask studentTask = new StudentTask();
//        studentTask.setStudent(studentRepository.findById(studentId).orElseThrow(RuntimeException::new));
//        studentTask.setTask(task);
//        studentTask.setVerdict(addTaskDTO.getVerdict());
//        studentTaskRepository.save(studentTask);
//    }

    @Override
    public void addTask(AddTaskDTO addTaskDTO, Long studentId) {
        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
        Optional<StudentTaskInfoDTO> relation = studentTaskRepository.
                findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform(),
                        addTaskDTO.getTitle(), addTaskDTO.getLink());
        if (relation.isEmpty()) {
            taskService.addTaskIfNotExists(addTaskDTO);
            Task task = taskService.findByTitleAndLink(addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(RuntimeException::new);
            System.out.println(task);
            StudentTask studentTask = new StudentTask();
            studentTask.setStudent(studentRepository.findById(studentId).orElseThrow(RuntimeException::new));
            studentTask.setTask(task);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTaskRepository.save(studentTask);
        } else if(relation.get().getVerdict()!=addTaskDTO.getVerdict()) {
            //StudentTask studentTask = studentTaskRepository.findByStudentId(studentId).orElseThrow(RuntimeException::new);
            StudentTask studentTask = studentTaskRepository.findById(relation.get().getId()).orElseThrow(RuntimeException::new);
            studentTask.setVerdict(addTaskDTO.getVerdict());
            studentTaskRepository.save(studentTask);
        } else {
            System.out.println("No new content");
        }
    }

    private String removePrefix(String url){
        if (url.startsWith("http://")) {
            return url.substring(7);
        } else if (url.startsWith("https://")) {
            return url.substring(8);
        }
        return url;
    }
}
