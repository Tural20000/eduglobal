package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.UserProgressDto;
import com.eduglobal.backend.entity.LessonEntity;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.entity.UserProgressEntity;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.LessonRepository;
import com.eduglobal.backend.repository.UserProgressRepository;
import com.eduglobal.backend.repository.UserRepository;
import com.eduglobal.backend.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService {

	private final UserProgressRepository userProgressRepository;
	private final UserRepository userRepository;
	private final LessonRepository lessonRepository;

	@Override
	@Transactional(readOnly = true)
	public List<UserProgressDto> findByUserId(Long userId) {
		return userProgressRepository.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserProgressDto save(Long userId, Long lessonId, Boolean completed, Integer score) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new MyNotFoundException("İstifadəçi tapılmadı: " + userId));
		LessonEntity lesson = lessonRepository.findById(lessonId)
				.orElseThrow(() -> new MyNotFoundException("Dərs tapılmadı: " + lessonId));
		Optional<UserProgressEntity> existing = userProgressRepository.findByUserIdAndLessonId(userId, lessonId);
		UserProgressEntity entity;
		if (existing.isPresent()) {
			entity = existing.get();
			entity.setCompleted(completed != null ? completed : entity.getCompleted());
			entity.setScore(score != null ? score : entity.getScore());
		} else {
			entity = UserProgressEntity.builder().user(user).lesson(lesson)
					.completed(completed != null ? completed : false).score(score).build();
		}
		entity = userProgressRepository.save(entity);
		return toDto(entity);
	}

	private UserProgressDto toDto(UserProgressEntity e) {
		return UserProgressDto.builder().id(e.getId()).userId(e.getUser().getId()).lessonId(e.getLesson().getId())
				.completed(e.getCompleted()).score(e.getScore()).build();
	}
}
