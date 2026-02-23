package com.eduglobal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelTestResultDto {
	@NotBlank
	private String level; // A1, A2, B1, ...
	@NotNull
	private Integer score;
}
