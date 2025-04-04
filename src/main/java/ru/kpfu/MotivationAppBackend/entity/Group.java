package ru.kpfu.MotivationAppBackend.entity;

import java.util.List;
import java.util.UUID;

public class Group {
    private Integer id;
    private Teacher owner;
    private List<Student> members;
    private int groupGoal;
}
