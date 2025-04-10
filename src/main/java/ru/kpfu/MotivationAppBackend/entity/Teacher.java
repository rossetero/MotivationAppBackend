package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends User{
   // private List<Group> ownedGroups;
}
