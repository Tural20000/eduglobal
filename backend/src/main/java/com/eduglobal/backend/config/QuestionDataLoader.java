package com.eduglobal.backend.config;

import com.eduglobal.backend.entity.QuestionEntity;
import com.eduglobal.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@Order(9)
@RequiredArgsConstructor
@Slf4j
public class QuestionDataLoader implements CommandLineRunner {

	/**
	 * Səviyyə testinin sualları artıq birbaşa CSV fayldan oxunur,
	 * bu loader isə artıq istifadə olunmur.
	 * Boş saxlayırıq ki, startup zamanı heç bir SQL insert cəhdi olmasın.
	 */
	@Override
	public void run(String... args) {
		log.info("QuestionDataLoader is disabled – questions are loaded directly from data/questions.csv in QuestionServiceImpl.");
	}
}

