package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.NewsDto;
import com.eduglobal.backend.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

	private final NewsService newsService;

	public NewsController(NewsService newsService) {
		this.newsService = newsService;
	}

	@GetMapping
	public ResponseEntity<List<NewsDto>> getLatest(
			@RequestParam(defaultValue = "10") int limit) {
		return ResponseEntity.ok(newsService.findLatest(Math.min(limit, 50)));
	}
}
