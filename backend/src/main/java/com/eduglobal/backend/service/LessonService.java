package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.LessonCreateDto;
import com.eduglobal.backend.dto.LessonDto;
import com.eduglobal.backend.enums.Category;

import java.util.List;

public interface LessonService {
	List<LessonDto> findAll();

	List<LessonDto> findByLevelId(Long levelId);

	List<LessonDto> findByLevelIdAndCategory(Long levelId, Category category);

	LessonDto findById(Long id);

	LessonDto create(LessonCreateDto dto);

	LessonDto update(Long id, LessonCreateDto dto);

	void deleteById(Long id);
}
