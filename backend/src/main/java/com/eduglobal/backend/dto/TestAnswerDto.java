package com.eduglobal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestAnswerDto {

	@NotNull
	private Long questionId;

	/**
	 * Istifadəçinin seçdiyi cavab varianti: "A", "B", "C" və ya "D".
	 */
	@NotBlank
	private String selectedOption;
}

