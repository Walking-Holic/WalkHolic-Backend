package com.example.OpenSource.domain.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseSummaryDto {
    private int steps;
    private int durationMinutes;
    private int caloriesBurned;

    public static ExerciseSummaryDto merge(ExerciseSummaryDto dto1, ExerciseSummaryDto dto2) {
        ExerciseSummaryDto mergedDto = new ExerciseSummaryDto();
        mergedDto.setSteps(dto1.getSteps() + dto2.getSteps());
        mergedDto.setDurationMinutes(dto1.getDurationMinutes() + dto2.getDurationMinutes());
        mergedDto.setCaloriesBurned(dto1.getCaloriesBurned() + dto2.getCaloriesBurned());

        return mergedDto;
    }
}
