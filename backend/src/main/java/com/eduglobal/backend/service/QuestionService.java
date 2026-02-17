package com.eduglobal.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.QuestionDto;
import com.eduglobal.backend.entity.QuestionEntity;

@Service
public interface QuestionService {
	List<QuestionDto> getTestQuestions();

	List<QuestionEntity> getRandomQuestionsForExam();

	List<QuestionEntity> getRandomQuestions(String level);

}
