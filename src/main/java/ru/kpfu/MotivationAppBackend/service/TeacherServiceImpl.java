package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;
import ru.kpfu.MotivationAppBackend.entity.*;
import ru.kpfu.MotivationAppBackend.repository.GroupRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentGroupRepository;
import ru.kpfu.MotivationAppBackend.repository.StudentRepository;
import ru.kpfu.MotivationAppBackend.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, GroupRepository groupRepository, StudentRepository studentRepository, StudentGroupRepository studentGroupRepository) {
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public TeacherProfileDTO getTeacherProfile(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(RuntimeException::new);
        return new TeacherProfileDTO(teacher.getId(), teacher.getLogin(), teacher.getName());
    }

    @Override
    public void editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long teacherId) {
        Teacher t = teacherRepository.findById(teacherId).orElseThrow();
        System.out.println(t);
        System.out.println(teacherProfileDTO);
        t.setName(teacherProfileDTO.getName());
        teacherRepository.save(t);
    }

    @Override
    public List<GroupDTO> getTeachersGroups(Long teacherId) {
        return groupRepository.findAllGroupDTOsForOwner(teacherId);
    }

    @Override
    public GroupDTO getTeachersGroupInfo(Long teacherId, Long groupId) {
        return groupRepository.findGroupDTOByIdAndOwner(teacherId, groupId);
    }

    @Override
    public void editTeachersGroup(GroupDTO groupDTO, Long teacherId, Long groupId) {
        List<Long> teacherGroupIds = getTeachersGroups(teacherId).stream().map(GroupDTO::getId).toList();
        if (teacherGroupIds.contains(groupId)) {
            Group g = groupRepository.findById(groupId).orElseThrow(RuntimeException::new);
            g.setName(groupDTO.getName());
            g.setGroupGoal(groupDTO.getGroupGoal());
            groupRepository.save(g);
        } else {
            throw new RuntimeException("This teacher with id = "+teacherId+" doesn't own this group");
        }
    }

    @Override
    public GroupDTO createGroup(Long teacherId, String name, int groupGoal) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(RuntimeException::new);
        Group group = new Group();
        group.setName(name);
        group.setGroupGoal(groupGoal);
        group.setOwner(teacher);
        groupRepository.save(group);
        return groupRepository.findAllGroupDTOsForOwner(teacherId).getLast();
    }

    //проверять владеет ли учитель группой
    //проверять существование отношения чтобы реализовать обновление
    @Override
    public void addStudentInGroup(Long teacherId, Long groupId, String studentLogin,int studentGoal) {
        Student student = studentRepository.findByLogin(studentLogin).orElseThrow(()->new RuntimeException("User "+studentLogin+" doesn't exists"));
        Group group = groupRepository.findById(groupId).orElseThrow(()->new RuntimeException("Group id = "+groupId+" doesn't exists"));
        List<Long> teacherGroupIds = getTeachersGroups(teacherId).stream().map(GroupDTO::getId).toList();
        if (!teacherGroupIds.contains(groupId)){
            throw new RuntimeException("Teacher "+teacherId+" doesnt own group "+groupId);
        }
        Optional<StudentGroup> relation = studentGroupRepository.findByStudentIdAndGroupId(student.getId(),groupId);
        if (relation.isEmpty()) {
            StudentGroup sg = new StudentGroup();
            sg.setStudent(student);
            sg.setGroup(group);
            sg.setStudentGoal(studentGoal);
            studentGroupRepository.save(sg);
        } else if (relation.get().getStudentGoal() != studentGoal) {
            StudentGroup sg = relation.get();
            sg.setStudentGoal(studentGoal);
            studentGroupRepository.save(sg);
        } else {
            System.out.println("No new content");
        }
    }

//    @Override
//    public void addTask(AddTaskDTO addTaskDTO, Long studentId) {
//        addTaskDTO.setLink(removePrefix(addTaskDTO.getLink()));
//        Optional<StudentTaskInfoDTO> relation = studentTaskRepository.
//                findByStudentTaskByPlatformAndTitleAndLink(studentId, addTaskDTO.getPlatform(),
//                        addTaskDTO.getTitle(), addTaskDTO.getLink());
//        if (relation.isEmpty()) {
//            taskService.addTaskIfNotExists(addTaskDTO);
//            Task task = taskService.findByTitleAndLink(addTaskDTO.getTitle(), addTaskDTO.getLink()).orElseThrow(RuntimeException::new);
//            StudentTask studentTask = new StudentTask();
//            studentTask.setStudent(studentRepository.findById(studentId).orElseThrow(RuntimeException::new));
//            studentTask.setTask(task);
//            studentTask.setVerdict(addTaskDTO.getVerdict());
//            studentTaskRepository.save(studentTask);
//        } else if(relation.get().getVerdict()!=addTaskDTO.getVerdict()) {
//            //StudentTask studentTask = studentTaskRepository.findByStudentId(studentId).orElseThrow(RuntimeException::new);
//            StudentTask studentTask = studentTaskRepository.findById(relation.get().getId()).orElseThrow(RuntimeException::new);
//            studentTask.setVerdict(addTaskDTO.getVerdict());
//            studentTaskRepository.save(studentTask);
//        } else {
//            System.out.println("No new content");
//        }
//    }
}
