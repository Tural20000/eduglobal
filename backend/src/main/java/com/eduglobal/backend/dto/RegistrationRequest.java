package com.eduglobal.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

	@NotBlank(message = "Username tələb olunur")
	@Size(min = 3, max = 50, message = "Username 3-50 simvol arasında olmalıdır")
	private String username;

	@NotBlank(message = "Password tələb olunur")
	@Size(min = 6, message = "Password ən azı 6 simvol olmalıdır")
	private String password;

	@NotBlank(message = "Email tələb olunur")
	@Email(message = "Düzgün email formatı daxil edin")
	private String email;

	@NotBlank(message = "Ad tələb olunur")
	@Size(min = 2, max = 100, message = "Ad 2-100 simvol arasında olmalıdır")
	private String name;
}
