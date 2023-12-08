package com.example.OpenSource.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseSummaryDto {
    private int steps;
    private int durationMinutes;
    private int caloriesBurned;
}
