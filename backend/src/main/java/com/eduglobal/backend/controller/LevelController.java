package com.eduglobal.backend.controller;

import com.eduglobal.backend.dto.LevelDto;
import com.eduglobal.backend.enums.Level;
import com.eduglobal.backend.service.LevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
@CrossOrigin(origins = "*")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public ResponseEntity<List<LevelDto>> findAll() {
        return ResponseEntity.ok(levelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LevelDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(levelService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<LevelDto> findByName(@PathVariable Level name) {
        return ResponseEntity.ok(levelService.findByName(name));
    }
}
