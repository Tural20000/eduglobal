package com.eduglobal.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.QuestionDto;
import com.eduglobal.backend.dto.TestSubmitRequestDto;
import com.eduglobal.backend.dto.TestSubmitResponseDto;
import com.eduglobal.backend.entity.QuestionEntity;

@Service
public interface QuestionService {

	/**
	 * Səviyyə testinə başlamaq üçün hər səviyyədən 5 sual (cəmi 30 sual) qaytarır.
	 * Cavablarda səviyyə məlumatı olmur.
	 */
	List<QuestionDto> getTestQuestions();

	List<QuestionEntity> getRandomQuestionsForExam();

	List<QuestionEntity> getRandomQuestions(String level);

	/**
	 * İstifadəçinin verdiyi cavablar əsasında düzgün cavab sayını hesablayır,
	 * səviyyəni müəyyən edir və nəticəni istifadəçi profiline yazır.
	 */
	TestSubmitResponseDto submitTest(String username, TestSubmitRequestDto request);

}

