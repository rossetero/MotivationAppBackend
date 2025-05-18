package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

@Getter
@Setter
@Entity
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    private int studentGoal;
    private int studentCurrentScore = 0;
}