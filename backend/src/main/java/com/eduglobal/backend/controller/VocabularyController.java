package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.VocabularyCreateDto;
import com.eduglobal.backend.dto.VocabularyDto;
import com.eduglobal.backend.service.VocabularyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabulary")
@CrossOrigin(origins = "*")
public class VocabularyController {

	private final VocabularyService vocabularyService;

	public VocabularyController(VocabularyService vocabularyService) {
		this.vocabularyService = vocabularyService;
	}

	@GetMapping
	public ResponseEntity<List<VocabularyDto>> findAll(@RequestParam(required = false) Long levelId) {
		if (levelId != null) {
			return ResponseEntity.ok(vocabularyService.findByLevelId(levelId));
		}
		return ResponseEntity.ok(vocabularyService.findAll());
	}

	@GetMapping("/word-of-the-day")
	public ResponseEntity<VocabularyDto> wordOfTheDay(@RequestParam(required = false) String date) {
		if (date == null || date.isEmpty()) {
			date = java.time.LocalDate.now().toString();
		}
		try {
			return ResponseEntity.ok(vocabularyService.getWordOfTheDay(date));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id:\\d+}")
	public ResponseEntity<VocabularyDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(vocabularyService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VocabularyDto> create(@Valid @RequestBody VocabularyCreateDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(vocabularyService.create(dto));
	}

	@PutMapping("/{id:\\d+}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VocabularyDto> update(@PathVariable Long id, @Valid @RequestBody VocabularyCreateDto dto) {
		return ResponseEntity.ok(vocabularyService.update(id, dto));
	}

	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		vocabularyService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
