package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.UserProgressDto;

import java.util.List;

public interface UserProgressService {
	List<UserProgressDto> findByUserId(Long userId);

	UserProgressDto save(Long userId, Long lessonId, Boolean completed, Integer score);
}
