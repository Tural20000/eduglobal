package com.eduglobal.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduglobal.backend.entity.QuestionEntity;
import com.eduglobal.backend.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {

	private final QuestionService questionService;

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("/exam")
	public List<QuestionEntity> getExamQuestions() {
		return questionService.getRandomQuestionsForExam();
	}

	@GetMapping("/{level}")
	public List<QuestionEntity> getQuestionsByLevel(@PathVariable String level) {
		return questionService.getRandomQuestions(level);
	}
}
