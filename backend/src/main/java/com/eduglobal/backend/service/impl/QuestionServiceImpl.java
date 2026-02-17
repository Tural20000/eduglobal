package com.eduglobal.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.QuestionDto;
import com.eduglobal.backend.entity.QuestionEntity;
import com.eduglobal.backend.repository.QuestionRepository;
import com.eduglobal.backend.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;

	public QuestionServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@Override
	public List<QuestionEntity> getRandomQuestionsForExam() {

		List<QuestionEntity> examQuestions = new ArrayList<>();

		examQuestions.addAll(questionRepository.findRandomByLevel("A1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("A2"));
		examQuestions.addAll(questionRepository.findRandomByLevel("B1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("B2"));
		examQuestions.addAll(questionRepository.findRandomByLevel("C1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("C2"));

		return examQuestions;
	}

	@Override
	public List<QuestionDto> getTestQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<QuestionEntity> getRandomQuestions(String level) {
		return questionRepository.findRandomByLevel(level);
	}

}
