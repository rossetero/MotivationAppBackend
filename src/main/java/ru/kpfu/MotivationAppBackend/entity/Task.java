package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.List;

@Getter
@Setter
@ToString(exclude = "studentsTasks")
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @NotNull
    private String number;
    @NotNull
    private String title;
    private double difficulty;
    @NotNull
    private String link;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    List<StudentTask> studentsTasks;
}
