package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private int groupGoal;
    private double minAvgDifficulty;
    private LocalDate dueDate; //yyyy-mm-dd
    private double easyMediumThreshold; //с какой сложности задача считается средней
    private double mediumHardThreshold; //с какой сложности задача считается сложной
    private String ownerName;
    private Long ownerId;
}
