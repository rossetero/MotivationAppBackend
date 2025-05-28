package ru.kpfu.MotivationAppBackend.dto.сf;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {
    private String status;
    private List<SubmissionDTO> result;

    // геттеры и сеттеры
}

