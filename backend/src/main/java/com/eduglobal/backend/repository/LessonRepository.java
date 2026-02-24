package com.eduglobal.backend.repository;

import com.eduglobal.backend.enums.Category;
import com.eduglobal.backend.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
	List<LessonEntity> findByLevelId(Long levelId);

	List<LessonEntity> findByLevelIdAndCategory(Long levelId, Category category);

	List<LessonEntity> findByCategoryOrderByTitleAsc(Category category);
}
