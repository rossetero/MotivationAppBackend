package ru.kpfu.MotivationAppBackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.GroupDTOWithMembers;
import ru.kpfu.MotivationAppBackend.dto.GroupResultDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentProfileDTOEnchanced;
import ru.kpfu.MotivationAppBackend.entity.Group;
import ru.kpfu.MotivationAppBackend.entity.Student;
import ru.kpfu.MotivationAppBackend.entity.StudentGroup;
import ru.kpfu.MotivationAppBackend.repository.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<StudentProfileDTOEnchanced> members = group.getMembers().stream()
                .map(studentGroup -> {
                    Student student = studentGroup.getStudent();
                    return new StudentProfileDTOEnchanced(
                            student.getId(),
                            student.getLogin(),
                            student.getName(),
                            student.getCfHandler(),
                            student.getAcmpId(),
                            studentGroup.getStudentGoal(),
                            studentGroup.getStudentCurrentScore()
                    );
                }).toList();

        return new GroupDTOWithMembers(
                group.getId(),
                group.getName(),
                group.getGoalSetTime(),
                group.getDueDate(),
                group.getGroupGoal(),
                group.getMinAvgDifficulty(),
                group.getOwner().getName(),
                group.getOwner().getId(),
                members
        );
    }

    @Override
    public GroupResultDTO getGroupResult(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        List<StudentGroup> members = group.getMembers();
        int currentGroupScore = members.stream().mapToInt(StudentGroup::getStudentCurrentScore).sum();
        List<String> failingStudents = members.stream().filter(m-> m.getStudentCurrentScore()<m.getStudentGoal()).map(m->m.getStudent().getName()).toList();
        boolean isSuccess = (currentGroupScore >= group.getGroupGoal()) && failingStudents.isEmpty();
        return new GroupResultDTO(groupId,group.getMinAvgDifficulty(), group.getGroupGoal(),currentGroupScore,isSuccess,failingStudents);
    }
}
