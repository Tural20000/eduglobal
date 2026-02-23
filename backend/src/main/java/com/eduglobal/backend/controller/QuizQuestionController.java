package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.QuizQuestionCreateDto;
import com.eduglobal.backend.dto.QuizQuestionDto;
import com.eduglobal.backend.service.QuizQuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-questions")
@CrossOrigin(origins = "*")
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<QuizQuestionDto>> findByLessonId(@PathVariable Long lessonId) {
        return ResponseEntity.ok(quizQuestionService.findByLessonId(lessonId));
    }

    @GetMapping("/level/{levelId}/random")
    public ResponseEntity<List<QuizQuestionDto>> findRandomByLevel(
            @PathVariable Long levelId,
            @RequestParam(defaultValue = "30") int limit) {
        return ResponseEntity.ok(quizQuestionService.findRandomByLevelId(levelId, limit));
    }

    @GetMapping("/exam")
    public ResponseEntity<List<QuizQuestionDto>> findRandomForExam(@RequestParam(defaultValue = "30") int limit) {
        return ResponseEntity.ok(quizQuestionService.findRandomForExam(limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(quizQuestionService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizQuestionDto> create(@Valid @RequestBody QuizQuestionCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizQuestionService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizQuestionDto> update(@PathVariable Long id, @Valid @RequestBody QuizQuestionCreateDto dto) {
        return ResponseEntity.ok(quizQuestionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quizQuestionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
