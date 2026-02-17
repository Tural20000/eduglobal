package com.eduglobal.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.ReviewDto;
import com.eduglobal.backend.entity.Review;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.ReviewRepository;
import com.eduglobal.backend.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	@Override
	public Review saveReview(ReviewDto reviewDto) {
		Review review = new Review();
		review.setName(reviewDto.getName());
		review.setText(reviewDto.getText());
		return reviewRepository.save(review);

	}

	@Override
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	@Override
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}

	@Override
	public Review updateReview(Long id, ReviewDto dto) {
		Review review = reviewRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Review not found"));

		review.setName(dto.getName());
		review.setText(dto.getText());

		return reviewRepository.save(review);
	}

}
