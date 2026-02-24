package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.WordDto;

import java.util.List;

public interface WordService {

	List<WordDto> findAll();

	void loadFromCsvIfEmpty();
}
