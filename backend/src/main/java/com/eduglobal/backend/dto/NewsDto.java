package com.eduglobal.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
}
