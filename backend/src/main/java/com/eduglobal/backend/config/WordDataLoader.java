package com.eduglobal.backend.config;

import com.eduglobal.backend.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(10)
@RequiredArgsConstructor
public class WordDataLoader implements CommandLineRunner {

	private final WordService wordService;

	@Override
	public void run(String... args) {
		wordService.loadFromCsvIfEmpty();
	}
}
