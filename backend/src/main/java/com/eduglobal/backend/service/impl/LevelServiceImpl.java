package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.LevelDto;
import com.eduglobal.backend.entity.LevelEntity;
import com.eduglobal.backend.enums.Level;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.LevelRepository;
import com.eduglobal.backend.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

	private final LevelRepository levelRepository;

	@Override
	@Transactional(readOnly = true)
	public List<LevelDto> findAll() {
		return levelRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public LevelDto findById(Long id) {
		return levelRepository.findById(id).map(this::toDto)
				.orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public LevelDto findByName(Level name) {
		return levelRepository.findByName(name).map(this::toDto)
				.orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + name));
	}

	private LevelDto toDto(LevelEntity e) {
		return LevelDto.builder().id(e.getId()).name(e.getName()).build();
	}
}
