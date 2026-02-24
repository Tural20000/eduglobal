package com.eduglobal.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordDto {
	private Long id;
	private String word;
	private String translation;
}
