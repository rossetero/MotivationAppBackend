package ru.kpfu.MotivationAppBackend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;
import ru.kpfu.MotivationAppBackend.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/teacher")
public class TeacherControllerImpl implements TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherControllerImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/profile")
    @Override
    public TeacherProfileDTO getTeacherProfile(@PathVariable Long userId) {
        return teacherService.getTeacherProfile(userId);
    }

    @PutMapping("/profile/edit")
    @Override
    public ResponseEntity<String> editTeacherProfile(@RequestBody @Valid TeacherProfileDTO teacherProfileDTO, @PathVariable Long userId) {
        teacherService.editTeacherProfile(teacherProfileDTO, userId);
        return ResponseEntity.ok("Profile Updated");
    }

    @GetMapping("/groups")
    @Override
    public List<GroupDTO> getTeachersGroups(@PathVariable Long userId) {
        return teacherService.getTeachersGroups(userId);
    }

    @GetMapping("/groups/{groupId}")
    @Override
    public GroupDTO getTeachersGroupInfo(@PathVariable Long userId,@PathVariable Long groupId) {
        return teacherService.getTeachersGroupInfo(userId,groupId);
    }

    @PutMapping("/groups/{groupId}/edit")
    @Override
    public ResponseEntity<String> editTeachersGroupInfo(@RequestBody @Valid GroupDTO groupDTO, @PathVariable Long userId, @PathVariable Long groupId) {
        teacherService.editTeachersGroup(groupDTO,userId,groupId);
        return ResponseEntity.ok("Group updated");
    }

    @PutMapping("/groups/{groupId}/addStudent")
    @Override
    public ResponseEntity<String> addStudentInGroup(@PathVariable Long userId,@PathVariable Long groupId,@RequestParam(value = "studentLogin") String studentLogin,@RequestParam(value = "studentGoal") int studentGoal) {
        teacherService.addStudentInGroup(userId,groupId,studentLogin,studentGoal);
        return ResponseEntity.ok("Student added");
    }

    @PostMapping("/groups/create")
    @Override
    public GroupDTO createGroup(@PathVariable Long userId,@RequestParam(value = "name") String name,@RequestParam(value = "groupGoal") int groupGoal) {
        return teacherService.createGroup(userId, name, groupGoal);
    }


}