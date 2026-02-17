package com.eduglobal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eduglobal.backend.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
	List<QuestionEntity> findByLevel(String level);

	@Query(value = """
			    SELECT * FROM questions
			    WHERE level = :level
			    ORDER BY RAND()
			    LIMIT 5
			""", nativeQuery = true)
	List<QuestionEntity> findRandomByLevel(@Param("level") String level);
}
