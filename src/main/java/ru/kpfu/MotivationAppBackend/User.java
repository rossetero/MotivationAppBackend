package ru.kpfu.MotivationAppBackend;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class User {
    private UUID id;
    private String name;
    private Role role;
}
