package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO;
import ru.kpfu.MotivationAppBackend.entity.StudentTask;

import java.util.List;

public interface StudentTaskRepository extends JpaRepository<StudentTask,Long> {
    @Query("""
        SELECT new ru.kpfu.MotivationAppBackend.dto.StudentTaskInfoDTO(
            st.student.id,
            t.platform, 
            t.title, 
            t.link, 
            st.verdict
        )
        FROM StudentTask st
        JOIN st.task t
        WHERE st.student.id = :studentId
        """)
    List<StudentTaskInfoDTO> findAllStudentTaskInfoByStudentId(@Param("studentId") Long studentId);



}
