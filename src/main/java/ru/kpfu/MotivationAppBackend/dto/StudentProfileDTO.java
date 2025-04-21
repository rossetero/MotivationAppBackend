package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//id login name
@Getter
@Setter
@AllArgsConstructor
public class StudentProfileDTO {
    private Long id;
    private String login;
    private String name;
    private String cfHandler;
    private String acmpId;
}
