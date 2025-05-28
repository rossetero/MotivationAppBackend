package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileDTOEnchanced extends StudentProfileDTO{
    private int studentGoal;
    private int studentCurrentScore;
    public StudentProfileDTOEnchanced(Long id, String login, String name, String cfHandler, String acmpId,int studentGoal,int studentCurrentScore) {
        super(id, login, name, cfHandler, acmpId);
        this.studentGoal=studentGoal;
        this.studentCurrentScore=studentCurrentScore;
    }
}
