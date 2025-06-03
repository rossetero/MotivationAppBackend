package ru.kpfu.MotivationAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTOEnchanced extends GroupDTO{
    private int currentGroupScore;
    public GroupDTOEnchanced(GroupDTO groupDTO,int currentGroupScore){
        super(groupDTO.getId(), groupDTO.getName(),
                groupDTO.getGroupGoal(),groupDTO.getMinAvgDifficulty(),
                groupDTO.getDueDate(),groupDTO.getEasyMediumThreshold(),
                groupDTO.getMediumHardThreshold(),groupDTO.getOwnerName(),
                groupDTO.getOwnerId());
        this.currentGroupScore=currentGroupScore;
    }
}
