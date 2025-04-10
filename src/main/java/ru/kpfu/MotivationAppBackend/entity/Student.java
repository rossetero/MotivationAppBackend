package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.jdbc.core.SqlReturnType;

import java.util.List;
@Entity
@Table(name = "students")
@ToString(exclude = "studentsTasks",callSuper = true)
@Getter
@Setter
public class Student extends User{
    private String cfHandler;
    private String acmpId;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private List<StudentTask> studentsTasks;

    // private List<Group> participatedGroups;
}
