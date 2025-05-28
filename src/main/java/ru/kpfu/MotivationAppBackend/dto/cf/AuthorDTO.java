package ru.kpfu.MotivationAppBackend.dto.cf;

import lombok.Data;

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




