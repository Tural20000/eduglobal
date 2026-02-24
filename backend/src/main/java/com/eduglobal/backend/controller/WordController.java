package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.WordDto;
import com.eduglobal.backend.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*")
public class WordController {

	private final WordService wordService;

	public WordController(WordService wordService) {
		this.wordService = wordService;
	}

	@GetMapping
	public ResponseEntity<List<WordDto>> getAll() {
		return ResponseEntity.ok(wordService.findAll());
	}
}
