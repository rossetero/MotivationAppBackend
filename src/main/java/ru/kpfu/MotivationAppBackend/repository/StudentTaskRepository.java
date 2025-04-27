package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.dto.AddTaskDTO;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentTaskRepository extends JpaRepository<StudentTask,Long> {
    @Query("""
    SELECT new ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO(
        studentTask.task.platform,
        studentTask.task.title,
        studentTask.task.link,
        studentTask.verdict
    )
    FROM StudentTask studentTask
    WHERE studentTask.student.id = :studentId
""")
    List<StudentTaskInfoDTO> findAllStudentTaskInfoByStudentId(@Param("studentId") Long studentId);

    @Query("""
    SELECT new ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO(
        studentTask.task.platform,
        studentTask.task.title,
        studentTask.task.link,
        studentTask.verdict
    )
    FROM StudentTask studentTask
    WHERE studentTask.student.id = :studentId and studentTask.task.platform = :platform
""")
    List<StudentTaskInfoDTO> findStudentTaskInfoByStudentIdAndPlatform(@Param("studentId") Long studentId, @Param("platform")Platform platform);

    @Query("""
    SELECT new ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO(
        studentTask.id,
        studentTask.task.platform,
        studentTask.task.title,
        studentTask.task.link,
        studentTask.verdict
    )
    FROM StudentTask studentTask
    WHERE studentTask.student.id = :studentId
    AND studentTask.task.platform = :platform
    AND studentTask.task.title = :title
    AND studentTask.task.link = :link
    """)
    Optional<StudentTaskInfoDTO> findByStudentTaskByPlatformAndTitleAndLink(
            @Param("studentId") Long studentId,
            @Param("platform") Platform platform,
            @Param("title") String title,
            @Param("link") String link
    );

    //Optional<StudentTask> findByStudentId(Long studentId);

}
