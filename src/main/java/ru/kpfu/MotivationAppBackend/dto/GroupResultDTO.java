package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResultDTO {
    private Long groupId;
    private LocalDate dueDate;
    private double minDifficulty;
    private int groupGoal;
    private int currentGroupScore;
    private boolean isSuccess;
    private List<String> failingStudents;
}
