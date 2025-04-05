package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

@Getter
@Setter
@Entity
@ToString(onlyExplicitlyIncluded = true)
public class StudentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @ToString.Include
    @Enumerated(EnumType.STRING)
    private Verdict verdict;
}
