package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTOWithMembers {

        private Long id;
        private String name;
        private int groupGoal;
        private double minAvgDifficulty;
        private String ownerName;
        private Long ownerId;
        private List<StudentProfileDTO> members;

}
