package com.eduglobal.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.ReviewDto;
import com.eduglobal.backend.entity.Review;
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

}
