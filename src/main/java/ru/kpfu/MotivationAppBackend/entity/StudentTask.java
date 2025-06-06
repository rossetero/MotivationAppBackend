package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kpfu.MotivationAppBackend.enums.Verdict;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StudentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Enumerated(EnumType.STRING)
    private Verdict verdict;
    private LocalDateTime lastChangedTime;
}
