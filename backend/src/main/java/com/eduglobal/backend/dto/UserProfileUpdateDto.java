package com.eduglobal.backend.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileUpdateDto {
	private String name;
	private String email;
	private String nickname;
}
