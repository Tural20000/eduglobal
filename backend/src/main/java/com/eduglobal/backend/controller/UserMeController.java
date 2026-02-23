package com.eduglobal.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.eduglobal.backend.dto.LevelTestResultDto;
import com.eduglobal.backend.dto.UserProfileDto;
import com.eduglobal.backend.dto.UserProfileUpdateDto;
import com.eduglobal.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/me")
@CrossOrigin(origins = "*")
public class UserMeController {

	private final UserService userService;

	public UserMeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<UserProfileDto> getProfile(@AuthenticationPrincipal UserDetails user) {
		if (user == null)
			return ResponseEntity.status(401).build();
		return ResponseEntity.ok(userService.getProfileByUsername(user.getUsername()));
	}

	@PatchMapping
	public ResponseEntity<UserProfileDto> updateProfile(
			@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody UserProfileUpdateDto dto) {
		if (user == null)
			return ResponseEntity.status(401).build();
		return ResponseEntity.ok(userService.updateProfile(user.getUsername(), dto));
	}

	@PostMapping("/level-test")
	public ResponseEntity<UserProfileDto> saveLevelTestResult(
			@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody LevelTestResultDto dto) {
		if (user == null)
			return ResponseEntity.status(401).build();
		return ResponseEntity.ok(userService.saveLevelTestResult(user.getUsername(), dto));
	}
}
