package ru.kpfu.MotivationAppBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;
import ru.kpfu.MotivationAppBackend.dto.GroupResultDTO;

import java.util.List;

public interface GroupController {
    @GetMapping("/groups")
    List<GroupDTO> getAllGroups();

    @GetMapping("/groups/{groupId}")
    GroupDTOWithMembers getGroupWithMembers(@PathVariable Long groupId);

    @GetMapping("/groups/{groupId}/result")
    GroupResultDTO getGroupResult(@PathVariable Long groupId);
}
