package com.eduglobal.backend.repository;

import com.eduglobal.backend.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {
    List<UserProgressEntity> findByUserId(Long userId);
    Optional<UserProgressEntity> findByUserIdAndLessonId(Long userId, Long lessonId);
}
