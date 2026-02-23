package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.QuizQuestionCreateDto;
import com.eduglobal.backend.dto.QuizQuestionDto;
import com.eduglobal.backend.entity.LessonEntity;
import com.eduglobal.backend.entity.QuizQuestionEntity;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.LessonRepository;
import com.eduglobal.backend.repository.QuizQuestionRepository;
import com.eduglobal.backend.service.QuizQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final LessonRepository lessonRepository;

    private static final int DEFAULT_EXAM_LIMIT = 30;

    @Override
    @Transactional(readOnly = true)
    public List<QuizQuestionDto> findByLessonId(Long lessonId) {
        return quizQuestionRepository.findByLessonId(lessonId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizQuestionDto> findRandomByLevelId(Long levelId, int limit) {
        int safeLimit = limit <= 0 ? DEFAULT_EXAM_LIMIT : Math.min(limit, 100);
        return quizQuestionRepository.findRandomByLevelIdLimit(levelId, safeLimit).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizQuestionDto> findRandomForExam(int limit) {
        int safeLimit = limit <= 0 ? DEFAULT_EXAM_LIMIT : Math.min(limit, 100);
        return quizQuestionRepository.findRandomLimit(safeLimit).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public QuizQuestionDto findById(Long id) {
        return quizQuestionRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new MyNotFoundException("Quiz sual tapılmadı: " + id));
    }

    @Override
    @Transactional
    public QuizQuestionDto create(QuizQuestionCreateDto dto) {
        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new MyNotFoundException("Dərs tapılmadı: " + dto.getLessonId()));
        String correct = (dto.getCorrectAnswer() == null || dto.getCorrectAnswer().isEmpty()) ? "A" : dto.getCorrectAnswer().toUpperCase().substring(0, 1);
        QuizQuestionEntity entity = QuizQuestionEntity.builder()
                .lesson(lesson).question(dto.getQuestion()).optionA(dto.getOptionA()).optionB(dto.getOptionB())
                .optionC(dto.getOptionC()).optionD(dto.getOptionD()).correctAnswer(correct).build();
        entity = quizQuestionRepository.save(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public QuizQuestionDto update(Long id, QuizQuestionCreateDto dto) {
        QuizQuestionEntity entity = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Quiz sual tapılmadı: " + id));
        LessonEntity lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new MyNotFoundException("Dərs tapılmadı: " + dto.getLessonId()));
        String correct = (dto.getCorrectAnswer() == null || dto.getCorrectAnswer().isEmpty()) ? "A" : dto.getCorrectAnswer().toUpperCase().substring(0, 1);
        entity.setLesson(lesson); entity.setQuestion(dto.getQuestion()); entity.setOptionA(dto.getOptionA()); entity.setOptionB(dto.getOptionB());
        entity.setOptionC(dto.getOptionC()); entity.setOptionD(dto.getOptionD()); entity.setCorrectAnswer(correct);
        entity = quizQuestionRepository.save(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!quizQuestionRepository.existsById(id)) throw new MyNotFoundException("Quiz sual tapılmadı: " + id);
        quizQuestionRepository.deleteById(id);
    }

    private QuizQuestionDto toDto(QuizQuestionEntity e) {
        return QuizQuestionDto.builder().id(e.getId()).lessonId(e.getLesson().getId()).question(e.getQuestion())
                .optionA(e.getOptionA()).optionB(e.getOptionB()).optionC(e.getOptionC()).optionD(e.getOptionD()).correctAnswer(e.getCorrectAnswer()).build();
    }
}
