package ru.kpfu.MotivationAppBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;
import ru.kpfu.MotivationAppBackend.repository.GroupRepository;
import ru.kpfu.MotivationAppBackend.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GroupControllerImpl implements GroupController {
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    @Autowired
    public GroupControllerImpl(GroupRepository groupRepository, GroupService groupService){
        this.groupRepository=groupRepository;
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    @Override
    public List<GroupDTO> getAllGroups(){
        return groupRepository.findAllGroupDTOs();
    }

    @GetMapping("/groups/{groupId}")
    @Override
    public GroupDTOWithMembers getGroupWithMembers(@PathVariable Long groupId){
        return groupService.getGroupWithMembers(groupId);
    }
}
