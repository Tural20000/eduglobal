package com.eduglobal.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduglobal.backend.dto.ReviewDto;
import com.eduglobal.backend.entity.Review;
import com.eduglobal.backend.service.ReviewService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/reviews")

public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;

	}

	@PostMapping
	public ResponseEntity<Review> createReview(@RequestBody ReviewDto reviewDto) {
		Review savedReview = reviewService.saveReview(reviewDto);
		return new ResponseEntity<>(savedReview, HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews() {
		return ResponseEntity.ok(reviewService.getAllReviews());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}

}
