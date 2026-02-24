package com.eduglobal.backend.service.impl;

import com.eduglobal.backend.dto.WordDto;
import com.eduglobal.backend.entity.WordEntity;
import com.eduglobal.backend.repository.WordRepository;
import com.eduglobal.backend.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordServiceImpl implements WordService {

	private final WordRepository wordRepository;

	private static final Set<String> HEADER_WORDS = Set.of("word", "english", "column1", "column2", "translation", "translaction");

	@Override
	@Transactional(readOnly = true)
	public List<WordDto> findAll() {
		return wordRepository.findAllByOrderByWordAsc().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void loadFromCsvIfEmpty() {
		ClassPathResource resource = new ClassPathResource("data/words.csv");
		if (!resource.exists()) {
			log.warn("data/words.csv not found on classpath, skipping");
			return;
		}
		wordRepository.deleteAllInBatch();

		int loaded = 0;
		int skipped = 0;
		try (var reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] parts = line.split(",", 2);
				String word = parts.length > 0 ? parts[0].trim() : "";
				String translation = parts.length > 1 ? parts[1].trim() : "";
				if (word.isEmpty())
					continue;
				String wordLower = word.toLowerCase();
				if (HEADER_WORDS.contains(wordLower))
					continue;
				if (wordRepository.existsByWordIgnoreCase(word)) {
					skipped++;
					continue;
				}
				wordRepository.save(WordEntity.builder()
						.word(word)
						.translation(translation)
						.build());
				loaded++;
			}
			log.info("Words loaded from CSV: {} inserted, {} skipped (duplicates)", loaded, skipped);
		} catch (Exception e) {
			log.error("Failed to load words from data/words.csv", e);
		}
	}

	private WordDto toDto(WordEntity e) {
		return WordDto.builder()
				.id(e.getId())
				.word(e.getWord())
				.translation(e.getTranslation())
				.build();
	}
}
