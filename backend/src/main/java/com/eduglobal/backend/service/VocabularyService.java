package com.eduglobal.backend.service;

import com.eduglobal.backend.dto.VocabularyCreateDto;
import com.eduglobal.backend.dto.VocabularyDto;

import java.util.List;

public interface VocabularyService {
	List<VocabularyDto> findAll();

	List<VocabularyDto> findByLevelId(Long levelId);

	VocabularyDto findById(Long id);

	/** Tarix əsaslı "günün sözü" – hər gün eyni söz. */
	VocabularyDto getWordOfTheDay(String dateYyyyMmDd);

	VocabularyDto create(VocabularyCreateDto dto);

	VocabularyDto update(Long id, VocabularyCreateDto dto);

	void deleteById(Long id);
}
