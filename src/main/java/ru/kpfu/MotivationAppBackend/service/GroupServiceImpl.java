package ru.kpfu.MotivationAppBackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.entity.Group;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.repository.GroupRepository;

import java.util.List;

@Service

public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupDTOWithMembers getGroupWithMembers(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        List<StudentProfileDTO> members = group.getMembers().stream()
                .map(studentGroup -> {
                    Student student = studentGroup.getStudent();
                    return new StudentProfileDTO(
                            student.getId(),
                            student.getLogin(),
                            student.getName(),
                            student.getCfHandler(),
                            student.getAcmpId()
                    );
                }).toList();

        return new GroupDTOWithMembers(
                group.getId(),
                group.getName(),
                group.getGroupGoal(),
                group.getMinAvgDifficulty(),
                group.getOwner().getName(),
                group.getOwner().getId(),
                members
        );
    }
}
