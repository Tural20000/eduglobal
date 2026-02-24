package com.eduglobal.backend.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduglobal.backend.dto.LevelTestResultDto;
import com.eduglobal.backend.dto.RegistrationRequest;
import com.eduglobal.backend.dto.UserProfileDto;
import com.eduglobal.backend.dto.UserProfileUpdateDto;
import com.eduglobal.backend.entity.RoleEntity;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.enums.UserRole;
import com.eduglobal.backend.exception.MyNotFoundException;
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

	@Transactional(readOnly = true)
	public UserProfileDto getProfileByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new MyNotFoundException("İstifadəçi tapılmadı"));
		return toProfileDto(user);
	}

	@Transactional
	public UserProfileDto updateProfile(String username, UserProfileUpdateDto dto) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new MyNotFoundException("İstifadəçi tapılmadı"));
		if (dto.getName() != null)
			user.setName(dto.getName());
		if (dto.getEmail() != null)
			user.setEmail(dto.getEmail());
		if (dto.getNickname() != null)
			user.setNickname(dto.getNickname());
		user = userRepository.save(user);
		return toProfileDto(user);
	}

	@Transactional
	public UserProfileDto saveLevelTestResult(String username, LevelTestResultDto dto) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new MyNotFoundException("İstifadəçi tapılmadı"));
		user.setLevelTestLevel(dto.getLevel());
		user.setLevelTestScore(dto.getScore());
		user.setLevelTestDate(LocalDateTime.now());
		user = userRepository.save(user);
		return toProfileDto(user);
	}

	private UserProfileDto toProfileDto(UserEntity u) {
		String levelName = u.getLevelTestLevel();
		if (levelName == null && u.getLevel() != null)
			levelName = u.getLevel().getName().name();
		return UserProfileDto.builder()
				.id(u.getId())
				.username(u.getUsername())
				.name(u.getName())
				.email(u.getEmail())
				.nickname(u.getNickname())
				.levelTestLevel(u.getLevelTestLevel())
				.levelTestScore(u.getLevelTestScore())
				.levelTestDate(u.getLevelTestDate())
				.levelName(levelName)
				.build();
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
		if (roleGet != null)
			roles.add(roleGet);
		user.setRoles(roles);
		return userRepository.save(user);
	}
}
