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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    private void resetStudentCurrentScores(Long groupId){
        List<StudentGroup> members = studentGroupRepository.findAllByGroupId(groupId);
        members.forEach(sg->sg.setStudentCurrentScore(0));
        studentGroupRepository.saveAll(members);
    }

    @Override
    public void editTeachersGroup(GroupDTO groupDTO, Long teacherId, Long groupId) {
        boolean f = false;
        List<Long> teacherGroupIds = getTeachersGroups(teacherId).stream().map(GroupDTO::getId).toList();
        if (teacherGroupIds.contains(groupId)) {
            Group g = groupRepository.findById(groupId).orElseThrow(RuntimeException::new);
            g.setName(groupDTO.getName());
            g.setEasyMediumThreshold(groupDTO.getEasyMediumThreshold());
            g.setMediumHardThreshold(groupDTO.getMediumHardThreshold());
            if(g.getGroupGoal()!=groupDTO.getGroupGoal() || g.getMinAvgDifficulty()!=groupDTO.getMinAvgDifficulty() || g.getDueDate()!=groupDTO.getDueDate()){
                g.setGroupGoal(groupDTO.getGroupGoal());
                g.setMinAvgDifficulty(groupDTO.getMinAvgDifficulty());
                g.setGoalSetTime(LocalDateTime.now());
                g.setDueDate(groupDTO.getDueDate());
                f=true;
            }
            groupRepository.save(g);
            if(f) resetStudentCurrentScores(groupId);
        } else {
            throw new RuntimeException("This teacher with id = "+teacherId+" doesn't own this group");
        }
    }

    @Override
    public GroupDTO createGroup(Long teacherId, String name, int groupGoal, double minAvgDifficulty, LocalDate dueDate,double easyMediumThreshold, double mediumHardThreshold) { //dd.mm.yy
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(RuntimeException::new);
        Group group = new Group();
        group.setName(name);
        group.setGroupGoal(groupGoal);
        group.setMinAvgDifficulty(minAvgDifficulty);
        group.setOwner(teacher);
        group.setGoalSetTime(LocalDateTime.now());
        group.setDueDate(dueDate);
        group.setEasyMediumThreshold(easyMediumThreshold);
        group.setMediumHardThreshold(mediumHardThreshold);
        groupRepository.save(group);
        return groupRepository.findAllGroupDTOsForOwner(teacherId).getLast();
    }

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

}
