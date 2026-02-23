package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.UserProgressDto;
import com.eduglobal.backend.service.UserProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-progress")
@CrossOrigin(origins = "*")
public class UserProgressController {

    private final UserProgressService userProgressService;

    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserProgressDto>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserProgressDto> save(
            @RequestParam Long userId,
            @RequestParam Long lessonId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) Integer score) {
        return ResponseEntity.ok(userProgressService.save(userId, lessonId, completed, score));
    }
}
