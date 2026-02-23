package com.eduglobal.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import com.eduglobal.backend.dto.RegistrationRequest;
import com.eduglobal.backend.entity.RoleEntity;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.enums.UserRole;
import com.eduglobal.backend.repository.RoleRepository;
import com.eduglobal.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserEntity registerUser(RegistrationRequest registrationRequest) {
		if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
			throw new RuntimeException("Bu istifadəçi adı artıq mövcuddur");
		}
		UserEntity user = new UserEntity();
		user.setUsername(registrationRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
		user.setEmail(registrationRequest.getEmail());
		user.setName(registrationRequest.getName());
		user.setUserRole(UserRole.USER);
		RoleEntity roleGet = roleRepository.findByName("ROLE_GET");
		Set<RoleEntity> roles = new HashSet<>();
		if (roleGet != null) roles.add(roleGet);
		user.setRoles(roles);
		return userRepository.save(user);
	}
}
