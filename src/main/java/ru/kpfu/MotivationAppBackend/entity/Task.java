package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String link;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    List<StudentTask> studentsTasks;
}
