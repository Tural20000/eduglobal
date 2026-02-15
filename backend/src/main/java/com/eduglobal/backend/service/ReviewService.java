package com.eduglobal.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.ReviewDto;
import com.eduglobal.backend.entity.Review;

@Service
public interface ReviewService {
	Review saveReview(ReviewDto reviewDto);

	List<Review> getAllReviews();

	void deleteReview(Long id);

}
