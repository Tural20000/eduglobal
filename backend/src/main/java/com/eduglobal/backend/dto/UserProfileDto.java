package com.eduglobal.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
	private Long id;
	private String username;
	private String name;
	private String email;
	private String nickname;
	private String levelTestLevel;
	private Integer levelTestScore;
	private LocalDateTime levelTestDate;
	private String levelName; // A1, A2, ... from level_id if set
}
