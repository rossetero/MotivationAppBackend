package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.jdbc.core.SqlReturnType;

import java.util.List;
@Entity
@Table(name = "students")

public class Student extends User{
    @Column(name = "cf_handle")
    private String cfHandle;
    @Column(name = "acmp_id")
    private String acmpId;
   // private List<Group> participatedGroups;
  //  private List<Task> studentsTasks;
}
