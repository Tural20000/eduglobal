package com.eduglobal.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgressDto {
	private Long id;
	private Long userId;
	private Long lessonId;
	private Boolean completed;
	private Integer score;
}
