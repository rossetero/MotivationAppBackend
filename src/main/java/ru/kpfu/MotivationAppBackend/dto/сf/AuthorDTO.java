package ru.kpfu.MotivationAppBackend.dto.сf;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class AuthorDTO {
    private Integer contestId;
    private Long participantId;
    private List<MemberDTO> members;
    private String participantType;
    private Boolean ghost;
    private Long startTimeSeconds;

    // геттеры и сеттеры
}




