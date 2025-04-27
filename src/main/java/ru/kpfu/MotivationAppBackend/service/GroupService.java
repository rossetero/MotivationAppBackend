package ru.kpfu.MotivationAppBackend.service;

import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;

public interface GroupService {
    public GroupDTOWithMembers getGroupWithMembers(Long groupId);
}
