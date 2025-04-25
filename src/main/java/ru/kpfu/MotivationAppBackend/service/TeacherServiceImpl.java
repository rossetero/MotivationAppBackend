package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;
import ru.kpfu.MotivationAppBackend.entity.Group;
import ru.kpfu.MotivationAppBackend.entity.Teacher;
import ru.kpfu.MotivationAppBackend.repository.GroupRepository;
import ru.kpfu.MotivationAppBackend.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
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
}
