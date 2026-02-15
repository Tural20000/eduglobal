package com.eduglobal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduglobal.backend.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
