package com.eduglobal.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false)
	private LessonEntity lesson;

	@Column(nullable = false, length = 1000)
	private String question;

	@Column(name = "option_a", nullable = false, length = 500)
	private String optionA;

	@Column(name = "option_b", nullable = false, length = 500)
	private String optionB;

	@Column(name = "option_c", length = 500)
	private String optionC;

	@Column(name = "option_d", length = 500)
	private String optionD;

	@Column(name = "correct_answer", nullable = false, length = 1)
	private String correctAnswer; // A, B, C, D
}
