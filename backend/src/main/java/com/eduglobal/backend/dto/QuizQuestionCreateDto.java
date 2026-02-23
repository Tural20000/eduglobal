package com.eduglobal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionCreateDto {
	@NotNull
	private Long lessonId;
	@NotBlank
	private String question;
	@NotBlank
	private String optionA;
	@NotBlank
	private String optionB;
	private String optionC;
	private String optionD;
	@NotBlank
	private String correctAnswer; // A, B, C, D
}
