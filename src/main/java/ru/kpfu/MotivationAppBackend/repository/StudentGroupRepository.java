package ru.kpfu.MotivationAppBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.MotivationAppBackend.entity.StudentGroup;

import java.util.Optional;
import java.util.List;
@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup,Long> {
    List<StudentGroup> findAllByGroupId(Long groupId);
    Optional<StudentGroup> findByStudentIdAndGroupId(Long studentId,Long groupId);
}
