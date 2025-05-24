package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResultDTO {
    private Long groupId;
    private int groupGoal;
    private int currentGroupScore;
    private boolean isSuccess;
    private List<String> failingStudents;
}
