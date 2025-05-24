package ru.kpfu.MotivationAppBackend.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;
import ru.kpfu.MotivationAppBackend.dto.GroupResultDTO;

public interface GroupService {
    GroupDTOWithMembers getGroupWithMembers(Long groupId);
    GroupResultDTO getGroupResult(Long groupId);

}
