package com.example.OpenSource.domain.exercise.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.exercise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping(value = "/save")
    public ResponseEntity<Void> saveExercise(@RequestBody ExerciseDto exerciseDto) {
        exerciseService.save(SecurityUtil.getCurrentMemberId(), exerciseDto);
        return ResponseEntity.ok().build();
    }
}
