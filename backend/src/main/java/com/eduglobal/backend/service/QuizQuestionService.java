package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.QuizQuestionCreateDto;
import com.eduglobal.backend.dto.QuizQuestionDto;

import java.util.List;

public interface QuizQuestionService {
	List<QuizQuestionDto> findByLessonId(Long lessonId);

	List<QuizQuestionDto> findRandomByLevelId(Long levelId, int limit);

	/** Bütün səviyyələrdən random suallar (Tez cavab / exam). */
	List<QuizQuestionDto> findRandomForExam(int limit);

	QuizQuestionDto findById(Long id);

	QuizQuestionDto create(QuizQuestionCreateDto dto);

	QuizQuestionDto update(Long id, QuizQuestionCreateDto dto);

	void deleteById(Long id);
}
