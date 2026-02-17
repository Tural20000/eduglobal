package com.eduglobal.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

	private Long id;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
}