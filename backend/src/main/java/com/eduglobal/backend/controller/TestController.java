package com.eduglobal.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduglobal.backend.dto.QuestionDto;
import com.eduglobal.backend.dto.TestSubmitRequestDto;
import com.eduglobal.backend.dto.TestSubmitResponseDto;
import com.eduglobal.backend.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

	private final QuestionService questionService;

	public TestController(QuestionService questionService) {
		this.questionService = questionService;
	}

	/**
	 * Səviyyə testinə start – hər level-dən 5 sual (cəmi 30 sual) +
	 * cavablarda səviyyə göstərilmir.
	 */
	@GetMapping("/start")
	public ResponseEntity<List<QuestionDto>> startTest() {
		List<QuestionDto> questions = questionService.getTestQuestions();
		return ResponseEntity.ok(questions);
	}

	/**
	 * İstifadəçinin cavablarını qəbul edib düzgün cavab sayını və səviyyəni
	 * qaytarır. Nəticə eyni zamanda istifadəçi profilinə yazılır.
	 */
	@PostMapping("/submit")
	public ResponseEntity<TestSubmitResponseDto> submitTest(
			@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody TestSubmitRequestDto request) {
		if (user == null) {
			return ResponseEntity.status(401).build();
		}
		TestSubmitResponseDto response = questionService.submitTest(user.getUsername(), request);
		return ResponseEntity.ok(response);
	}
}

