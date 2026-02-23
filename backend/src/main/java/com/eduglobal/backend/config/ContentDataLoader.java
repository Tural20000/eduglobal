package com.eduglobal.backend.config;

import com.eduglobal.backend.entity.LessonEntity;
import com.eduglobal.backend.entity.LevelEntity;
import com.eduglobal.backend.entity.QuizQuestionEntity;
import com.eduglobal.backend.entity.VocabularyEntity;
import com.eduglobal.backend.enums.Category;
import com.eduglobal.backend.enums.Level;
import com.eduglobal.backend.repository.LessonRepository;
import com.eduglobal.backend.repository.LevelRepository;
import com.eduglobal.backend.repository.QuizQuestionRepository;
import com.eduglobal.backend.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(2)
public class ContentDataLoader implements CommandLineRunner {

    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Override
    public void run(String... args) {
        LevelEntity a1 = levelRepository.findByName(Level.A1).orElse(null);
        if (a1 == null) return;

        if (vocabularyRepository.count() == 0) {
            vocabularyRepository.save(VocabularyEntity.builder().word("Hello").meaning("Salam").level(a1).build());
            vocabularyRepository.save(VocabularyEntity.builder().word("Book").meaning("Kitab").level(a1).build());
            vocabularyRepository.save(VocabularyEntity.builder().word("Water").meaning("Su").level(a1).build());
        }

        var grammarLessons = lessonRepository.findByLevelIdAndCategory(a1.getId(), Category.GRAMMAR);
        LessonEntity grammarLesson = grammarLessons.isEmpty() ? null : grammarLessons.get(0);
        if (grammarLesson == null) {
            String content = "To Be (Am/Is/Are) – Vəziyyət və ad bildirmək üçün istifadə olunur. Formula: I am / You are / He is.";
            grammarLesson = lessonRepository.save(LessonEntity.builder()
                    .title("To Be (Am/Is/Are)")
                    .content(content)
                    .level(a1)
                    .category(Category.GRAMMAR)
                    .build());
        }

        if (quizQuestionRepository.findByLessonId(grammarLesson.getId()).isEmpty()) {
            quizQuestionRepository.save(QuizQuestionEntity.builder()
                    .lesson(grammarLesson).question("I ___ a student.").optionA("am").optionB("is").optionC("are").optionD("be").correctAnswer("A").build());
            quizQuestionRepository.save(QuizQuestionEntity.builder()
                    .lesson(grammarLesson).question("She ___ to school.").optionA("go").optionB("goes").optionC("going").optionD("went").correctAnswer("B").build());
        }
    }
}
