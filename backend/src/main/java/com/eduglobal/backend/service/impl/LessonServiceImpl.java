package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.LessonCreateDto;
import com.eduglobal.backend.dto.LessonDto;
import com.eduglobal.backend.entity.LevelEntity;
import com.eduglobal.backend.entity.LessonEntity;
import com.eduglobal.backend.enums.Category;
import com.eduglobal.backend.exception.MyNotFoundException;
import com.eduglobal.backend.repository.LevelRepository;
import com.eduglobal.backend.repository.LessonRepository;
import com.eduglobal.backend.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LevelRepository levelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto> findAll() {
        return lessonRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto> findByLevelId(Long levelId) {
        return lessonRepository.findByLevelId(levelId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonDto> findByLevelIdAndCategory(Long levelId, Category category) {
        return lessonRepository.findByLevelIdAndCategory(levelId, category).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LessonDto findById(Long id) {
        return lessonRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new MyNotFoundException("Dərs tapılmadı: " + id));
    }

    @Override
    @Transactional
    public LessonDto create(LessonCreateDto dto) {
        LevelEntity level = levelRepository.findById(dto.getLevelId())
                .orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + dto.getLevelId()));
        LessonEntity entity = LessonEntity.builder()
                .title(dto.getTitle()).content(dto.getContent()).level(level).category(dto.getCategory()).build();
        entity = lessonRepository.save(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public LessonDto update(Long id, LessonCreateDto dto) {
        LessonEntity entity = lessonRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Dərs tapılmadı: " + id));
        LevelEntity level = levelRepository.findById(dto.getLevelId())
                .orElseThrow(() -> new MyNotFoundException("Level tapılmadı: " + dto.getLevelId()));
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setLevel(level);
        entity.setCategory(dto.getCategory());
        entity = lessonRepository.save(entity);
        return toDto(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!lessonRepository.existsById(id)) throw new MyNotFoundException("Dərs tapılmadı: " + id);
        lessonRepository.deleteById(id);
    }

    private LessonDto toDto(LessonEntity e) {
        return LessonDto.builder()
                .id(e.getId()).title(e.getTitle()).content(e.getContent())
                .levelId(e.getLevel().getId()).levelName(e.getLevel().getName().name()).category(e.getCategory()).build();
    }
}
