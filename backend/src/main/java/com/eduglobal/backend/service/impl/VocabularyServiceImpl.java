package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.VocabularyCreateDto;
import com.eduglobal.backend.dto.VocabularyDto;
import com.eduglobal.backend.entity.LevelEntity;
import com.eduglobal.backend.entity.VocabularyEntity;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.LevelRepository;
import com.eduglobal.backend.repository.VocabularyRepository;
import com.eduglobal.backend.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl implements VocabularyService {

	private final VocabularyRepository vocabularyRepository;
	private final LevelRepository levelRepository;

	@Override
	@Transactional(readOnly = true)
	public List<VocabularyDto> findAll() {
		return vocabularyRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<VocabularyDto> findByLevelId(Long levelId) {
		return vocabularyRepository.findByLevelId(levelId).stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public VocabularyDto findById(Long id) {
		return vocabularyRepository.findById(id).map(this::toDto)
				.orElseThrow(() -> new MyNotFoundException("Vocabulary tapılmadı: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public VocabularyDto getWordOfTheDay(String dateYyyyMmDd) {
		var all = vocabularyRepository.findAll();
		if (all.isEmpty())
			throw new MyNotFoundException("Lüğətdə söz yoxdur");
		int seed = (dateYyyyMmDd != null ? dateYyyyMmDd.replace("-", "") : "").hashCode();
		int index = Math.abs(seed) % all.size();
		return toDto(all.get(index));
	}

	@Override
	@Transactional
	public VocabularyDto create(VocabularyCreateDto dto) {
		LevelEntity level = levelRepository.findById(dto.getLevelId())
				.orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + dto.getLevelId()));
		VocabularyEntity entity = VocabularyEntity.builder().word(dto.getWord()).meaning(dto.getMeaning())
				.exampleSentence(dto.getExampleSentence()).level(level).build();
		entity = vocabularyRepository.save(entity);
		return toDto(entity);
	}

	@Override
	@Transactional
	public VocabularyDto update(Long id, VocabularyCreateDto dto) {
		VocabularyEntity entity = vocabularyRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("Vocabulary tapılmadı: " + id));
		LevelEntity level = levelRepository.findById(dto.getLevelId())
				.orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + dto.getLevelId()));
		entity.setWord(dto.getWord());
		entity.setMeaning(dto.getMeaning());
		entity.setExampleSentence(dto.getExampleSentence());
		entity.setLevel(level);
		entity = vocabularyRepository.save(entity);
		return toDto(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (!vocabularyRepository.existsById(id))
			throw new MyNotFoundException("Vocabulary tapılmadı: " + id);
		vocabularyRepository.deleteById(id);
	}

	private VocabularyDto toDto(VocabularyEntity e) {
		return VocabularyDto.builder().id(e.getId()).word(e.getWord()).meaning(e.getMeaning())
				.exampleSentence(e.getExampleSentence()).levelId(e.getLevel().getId())
				.levelName(e.getLevel().getName().name()).build();
	}
}
