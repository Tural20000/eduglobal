package com.eduglobal.backend.repository;

import com.eduglobal.backend.entity.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Long> {
    List<QuizQuestionEntity> findByLessonId(Long lessonId);

    @Query(value = "SELECT qq.* FROM quiz_questions qq INNER JOIN lessons l ON qq.lesson_id = l.id WHERE l.level_id = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<QuizQuestionEntity> findRandomByLevelIdLimit(Long levelId, int limit);

    @Query(value = "SELECT * FROM quiz_questions ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<QuizQuestionEntity> findRandomLimit(int limit);
}
