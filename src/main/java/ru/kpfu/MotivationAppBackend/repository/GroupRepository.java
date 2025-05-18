package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.dto.GroupDTO;
import ru.kpfu.MotivationAppBackend.entity.Group;

import java.util.List;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT new ru.kpfu.MotivationAppBackend.dto.GroupDTO(g.id, g.name, g.groupGoal,g.minAvgDifficulty, t.name, t.id) " +
            "FROM Group g JOIN g.owner t WHERE g.id = :groupId and t.id = :teacherId")
    GroupDTO findGroupDTOByIdAndOwner(@Param("teacherId") Long teacherId, @Param("groupId") Long groupId);


    @Query("SELECT new ru.kpfu.MotivationAppBackend.dto.GroupDTO(g.id, g.name, g.groupGoal,g.minAvgDifficulty, t.name, t.id) " +
            "FROM Group g JOIN g.owner t " +
            "WHERE t.id = :teacherId")
    List<GroupDTO> findAllGroupDTOsForOwner(@Param("teacherId") Long teacherId);

    @Query("SELECT new ru.kpfu.MotivationAppBackend.dto.GroupDTO(g.id, g.name, g.groupGoal,g.minAvgDifficulty, t.name, t.id) " +
            "FROM Group g JOIN g.owner t ")
    List<GroupDTO> findAllGroupDTOs();
}