package ru.kpfu.MotivationAppBackend.dto.сf;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ProblemDTO {
    private Integer contestId;
    private String index;
    private String name;
    private String type;
    private Integer rating;
    private List<String> tags;

    // геттеры и сеттеры
}
