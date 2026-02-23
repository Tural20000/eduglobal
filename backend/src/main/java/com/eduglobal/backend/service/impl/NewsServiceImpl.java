package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.NewsDto;
import com.eduglobal.backend.entity.NewsEntity;
import com.eduglobal.backend.repository.NewsRepository;
import com.eduglobal.backend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

	private final NewsRepository newsRepository;

	@Override
	@Transactional(readOnly = true)
	public List<NewsDto> findLatest(int limit) {
		return newsRepository.findTop20ByOrderByCreatedAtDesc().stream()
				.limit(limit)
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	private NewsDto toDto(NewsEntity e) {
		return NewsDto.builder()
				.id(e.getId())
				.title(e.getTitle())
				.content(e.getContent())
				.createdAt(e.getCreatedAt())
				.build();
	}
}
