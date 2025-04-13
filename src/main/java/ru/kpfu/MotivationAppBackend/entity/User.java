package ru.kpfu.MotivationAppBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kpfu.MotivationAppBackend.enums.Role;

import java.util.UUID;
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
}
