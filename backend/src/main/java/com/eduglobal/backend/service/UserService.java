package com.eduglobal.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eduglobal.backend.dto.RegistrationRequest;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntity registerUser(RegistrationRequest registrationRequest) {
		// İstifadəçi adının artıq mövcud olub olmadığını yoxla
		if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
			throw new RuntimeException("Bu istifadəçi adı artıq mövcuddur");
		}

		// Yeni istifadəçi yarat
		UserEntity user = new UserEntity();
		user.setUsername(registrationRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
		user.setEmail(registrationRequest.getEmail());
		user.setName(registrationRequest.getName());
		// Yeni istifadəçilər üçün rol təyin edilmir (boş Set)

		return userRepository.save(user);
	}
}
