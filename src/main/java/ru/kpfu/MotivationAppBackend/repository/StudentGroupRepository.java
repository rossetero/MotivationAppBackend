package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.entity.StudentGroup;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup,Long> {

    Optional<StudentGroup> findByStudentIdAndGroupId(Long studentId,Long groupId);
}
