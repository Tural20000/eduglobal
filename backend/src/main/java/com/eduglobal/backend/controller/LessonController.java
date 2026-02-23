package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.LessonCreateDto;
import com.eduglobal.backend.dto.LessonDto;
import com.eduglobal.backend.enums.Category;
import com.eduglobal.backend.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@CrossOrigin(origins = "*")
public class LessonController {

	private final LessonService lessonService;

	public LessonController(LessonService lessonService) {
		this.lessonService = lessonService;
	}

	@GetMapping
	public ResponseEntity<List<LessonDto>> findAll(@RequestParam(required = false) Long levelId,
			@RequestParam(required = false) Category category) {
		if (levelId != null && category != null) {
			return ResponseEntity.ok(lessonService.findByLevelIdAndCategory(levelId, category));
		}
		if (levelId != null) {
			return ResponseEntity.ok(lessonService.findByLevelId(levelId));
		}
		return ResponseEntity.ok(lessonService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<LessonDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(lessonService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LessonDto> create(@Valid @RequestBody LessonCreateDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.create(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LessonDto> update(@PathVariable Long id, @Valid @RequestBody LessonCreateDto dto) {
		return ResponseEntity.ok(lessonService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		lessonService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
