package com.example.OpenSource.domain.exercise.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.exercise.dto.ExerciseSummaryDto;
import com.example.OpenSource.domain.exercise.service.ExerciseService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/weekly")
    public ResponseEntity<List<ExerciseDto>> getWeeklyExerciseData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(
                exerciseService.getWeeklyExerciseData(SecurityUtil.getCurrentMemberId(), startDate, endDate));
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<ExerciseDto>> getMonthlyExerciseData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) int year,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) int month) {
        return ResponseEntity.ok(
                exerciseService.getMonthlyExerciseData(SecurityUtil.getCurrentMemberId(), year, month));
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<Map<YearMonth, ExerciseSummaryDto>> getYearlyExerciseData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) int year) {
        return ResponseEntity.ok(
                exerciseService.getYearlyExerciseData(SecurityUtil.getCurrentMemberId(), year));
    }

    @GetMapping("/all")
    public ResponseEntity<ExerciseSummaryDto> getAllExerciseData() {
        return ResponseEntity.ok(
                exerciseService.getAllExerciseData(SecurityUtil.getCurrentMemberId()));
    }
}
