package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;

import java.util.List;

public interface TeacherService {
    TeacherProfileDTO getTeacherProfile(Long teacherId);

    void editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long teacherId);

    List<GroupDTO> getTeachersGroups(Long teacherId);

    GroupDTO getTeachersGroupInfo(Long teacherId,Long groupId);

    void editTeachersGroup(GroupDTO groupDTO,Long teacherId,Long groupId);

    GroupDTO createGroup(Long teacherId ,String name, int groupGoal);
}