package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Entity
@Table(name = "groups")
@Getter
@Setter
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Teacher owner;
    private int groupGoal;
    //private List<Student> members;
}
