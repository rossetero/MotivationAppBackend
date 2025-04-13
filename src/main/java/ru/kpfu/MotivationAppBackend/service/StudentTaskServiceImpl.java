//package ru.kpfu.MotivationAppBackend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
//import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
//import ru.kpfu.MotivationAppBackend.entity.StudentTask;
//import ru.kpfu.MotivationAppBackend.enums.Platform;
//import ru.kpfu.MotivationAppBackend.repository.StudentTaskRepository;
//
//import java.util.Optional;
//
//public class StudentTaskServiceImpl {
//    StudentTaskRepository studentTaskRepository;
//    @Autowired
//    public StudentTaskServiceImpl(StudentTaskRepository studentTaskRepository){
//        this.studentTaskRepository=studentTaskRepository;
//    }
//    public void saveRelation(StudentTask studentTask){
//        studentTaskRepository.save(studentTask);
//    }
//
//    public Optional<StudentTaskInfoDTO> findByStudentTaskByPlatformAndTitleAndLink(AddTaskDTO addTaskDTO,Long studentId){
//        return studentTaskRepository.findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform().name(),addTaskDTO.getTitle(), addTaskDTO.getLink());
//    }
//}
