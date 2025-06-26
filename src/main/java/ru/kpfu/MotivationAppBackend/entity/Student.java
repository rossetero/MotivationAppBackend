package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@ToString(exclude = "studentTaskList", callSuper = true)
@Getter
@Setter
public class Student extends User {
    private String cfHandler;
    private String acmpId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private List<StudentTask> studentTaskList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private List<StudentGroup> participatedGroups;
}


