package com.eduglobal.backend.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduglobal.backend.dto.LevelTestResultDto;
import com.eduglobal.backend.dto.QuestionDto;
import com.eduglobal.backend.dto.TestAnswerDto;
import com.eduglobal.backend.dto.TestSubmitRequestDto;
import com.eduglobal.backend.dto.TestSubmitResponseDto;
import com.eduglobal.backend.entity.QuestionEntity;
import com.eduglobal.backend.repository.QuestionRepository;
import com.eduglobal.backend.service.QuestionService;
import com.eduglobal.backend.service.UserService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final UserService userService;

	public QuestionServiceImpl(QuestionRepository questionRepository, UserService userService) {
		this.questionRepository = questionRepository;
		this.userService = userService;
	}

	@Override
	public List<QuestionEntity> getRandomQuestionsForExam() {

		List<QuestionEntity> examQuestions = new ArrayList<>();

		examQuestions.addAll(questionRepository.findRandomByLevel("A1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("A2"));
		examQuestions.addAll(questionRepository.findRandomByLevel("B1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("B2"));
		examQuestions.addAll(questionRepository.findRandomByLevel("C1"));
		examQuestions.addAll(questionRepository.findRandomByLevel("C2"));

		return examQuestions;
	}

	/**
	 * Səviyyə testi üçün sualları birbaşa questions.csv faylından oxuyuruq ki,
	 * DB cədvəli ilə bağlı heç bir qarışıqlıq olmasın.
	 */
	@Override
	public List<QuestionDto> getTestQuestions() {
		List<CsvQuestion> all = loadCsvQuestions();

		// Hər səviyyədən 5 sual seç
		List<CsvQuestion> selected = new ArrayList<>();
		String[] levels = { "A1", "A2", "B1", "B2", "C1", "C2" };
		for (String level : levels) {
			List<CsvQuestion> byLevel = all.stream()
					.filter(q -> level.equalsIgnoreCase(q.level()))
					.collect(Collectors.toList());
			Collections.shuffle(byLevel);
			int limit = Math.min(5, byLevel.size());
			for (int i = 0; i < limit; i++) {
				selected.add(byLevel.get(i));
			}
		}

		// Əgər hər səviyyədən 5 tapılmadısa, qalan boşluğu ümumi siyahıdan tamamla
		if (selected.size() < 30 && all.size() > selected.size()) {
			List<CsvQuestion> remaining = new ArrayList<>(all);
			remaining.removeAll(selected);
			Collections.shuffle(remaining);
			int need = Math.min(30 - selected.size(), remaining.size());
			for (int i = 0; i < need; i++) {
				selected.add(remaining.get(i));
			}
		}

		// Ümumi siyahını qarışdır
		Collections.shuffle(selected);

		// DTO-ya xəritələndir – level sahəsini ötürmürük
		return selected.stream()
				.map(e -> new QuestionDto(
						e.id(),
						e.question(),
						e.optionA(),
						e.optionB(),
						e.optionC(),
						e.optionD()))
				.collect(Collectors.toList());
	}

	@Override
	public List<QuestionEntity> getRandomQuestions(String level) {
		return questionRepository.findRandomByLevel(level);
	}

	@Override
	@Transactional
	public TestSubmitResponseDto submitTest(String username, TestSubmitRequestDto request) {
		if (request == null || request.getAnswers() == null || request.getAnswers().isEmpty()) {
			return TestSubmitResponseDto.builder().totalCorrect(0).detectedLevel("A1").build();
		}

		// CSV-dən bütün sualları oxu və xəritə düzəlt (id -> CsvQuestion)
		Map<Long, CsvQuestion> questionMap = loadCsvQuestions().stream()
				.collect(Collectors.toMap(CsvQuestion::id, Function.identity()));

		int totalCorrect = 0;

		for (TestAnswerDto answer : request.getAnswers()) {
			CsvQuestion q = questionMap.get(answer.getQuestionId());
			if (q == null)
				continue;

			String correct = normalizeOption(q.correct());
			String given = normalizeOption(answer.getSelectedOption());

			if (correct != null && correct.equals(given)) {
				totalCorrect++;
			}
		}

		String detectedLevel = detectLevel(totalCorrect);

		// Nəticəni istifadəçi profiline yaz
		userService.saveLevelTestResult(username,
				LevelTestResultDto.builder()
						.level(detectedLevel)
						.score(totalCorrect)
						.build());

		return TestSubmitResponseDto.builder()
				.totalCorrect(totalCorrect)
				.detectedLevel(detectedLevel)
				.build();
	}

	private String normalizeOption(String value) {
		if (value == null)
			return null;
		String v = value.trim().toUpperCase(Locale.ROOT);
		// Ehtiyat üçün "OPTION_A" formatını da A-ya çevirək
		if (v.startsWith("OPTION_") && v.length() >= 8) {
			return String.valueOf(v.charAt(7));
		}
		// Əks halda ilk hərfi götürürük (A/B/C/D)
		return v.isEmpty() ? null : String.valueOf(v.charAt(0));
	}

	private String detectLevel(int totalCorrect) {
		if (totalCorrect <= 5)
			return "A1";
		if (totalCorrect <= 10)
			return "A2";
		if (totalCorrect <= 15)
			return "B1";
		if (totalCorrect <= 20)
			return "B2";
		if (totalCorrect <= 25)
			return "C1";
		return "C2";
	}

	/**
	 * `data/questions.csv` faylını oxuyur və bütün sualları yaddaşa yükləyir.
	 * Suallara stabil ID veririk ki, frontend həmin ID-lərlə cavab göndərə bilsin.
	 */
	private List<CsvQuestion> loadCsvQuestions() {
		List<CsvQuestion> result = new ArrayList<>();
		ClassPathResource resource = new ClassPathResource("data/questions.csv");
		if (!resource.exists()) {
			return result;
		}

		try (var reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean first = true;
			long idCounter = 1L;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;

				List<String> cols = parseCsvLine(line);
				if (cols.size() < 7)
					continue;

				// header sətrini keç
				if (first) {
					first = false;
					String firstCol = cols.get(0).trim().toLowerCase(Locale.ROOT);
					if ("question_text".equals(firstCol)) {
						continue;
					}
				}

				String questionText = cols.get(0).trim();
				String optionA = cols.get(1).trim();
				String optionB = cols.get(2).trim();
				String optionC = cols.get(3).trim();
				String optionD = cols.get(4).trim();
				String correct = cols.get(5).trim();
				String level = cols.get(6).trim();

				if (questionText.isEmpty() || level.isEmpty())
					continue;

				result.add(new CsvQuestion(
						idCounter++,
						questionText,
						optionA,
						optionB,
						optionC,
						optionD,
						correct,
						level));
			}
		} catch (Exception e) {
			// Sakitcə boş siyahı qaytarırıq; frontend "sual tapılmadı" mesajını göstərəcək
		}

		return result;
	}

	/**
	 * Sadə CSV parser – vergülləri dırnaq içində saymır.
	 */
	private List<String> parseCsvLine(String line) {
		List<String> cols = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean inQuotes = false;
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '\"') {
				inQuotes = !inQuotes;
			} else if (c == ',' && !inQuotes) {
				cols.add(current.toString());
				current.setLength(0);
			} else {
				current.append(c);
			}
		}
		cols.add(current.toString());
		return cols;
	}

	/**
	 * CSV-dən oxunan sual üçün daxili model.
	 */
	private record CsvQuestion(
			Long id,
			String question,
			String optionA,
			String optionB,
			String optionC,
			String optionD,
			String correct,
			String level) {
	}
}

