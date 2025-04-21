package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends User {
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private List<Group> ownedGroups;
}
