package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.LevelDto;
import com.eduglobal.backend.enums.Level;

import java.util.List;

public interface LevelService {
    List<LevelDto> findAll();
    LevelDto findById(Long id);
    LevelDto findByName(Level name);
}
