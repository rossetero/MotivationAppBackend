package ru.kpfu.MotivationAppBackend.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;

import java.util.List;

public interface TeacherController {
    @GetMapping("/profile")
    TeacherProfileDTO getTeacherProfile(Long userId);

    @PutMapping("/profile/edit")
    ResponseEntity<String> editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long userId);

    @GetMapping("/groups")
    List<GroupDTO> getTeachersGroups(Long userId);

    @GetMapping("/groups/{groupId}")
    GroupDTO getTeachersGroupInfo(Long userId, Long groupId);

    @PutMapping("/groups/{groupId}/edit")
    ResponseEntity<String> editTeachersGroupInfo(GroupDTO groupDTO,Long userId, Long groupId);

    @PutMapping("/groups/{groupId}/addStudent")
    ResponseEntity<String> addStudentInGroup(Long userId, Long groupId, String studentLogin,int studentGoal);

    @PostMapping("/groups/create")
    GroupDTO createGroup(Long userId, String name,int groupGoal,double minAvgDifficulty);
}
