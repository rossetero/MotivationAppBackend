package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentGoalsDTO {
    private Long id;
    private String name;
    private int groupGoal;
    private int studentGoal;
    private int studentCurrentScore;
    private double easyMediumThreshold; //с какой сложности задача считается средней
    private double mediumHardThreshold; //с какой сложности задача считается сложной
}
