package com.example.OpenSource.domain.exercise.dto;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import com.example.OpenSource.domain.member.domain.Member;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExerciseDto {
    private LocalDate date;
    private int steps;
    private int durationMinutes;
    private int caloriesBurned;

    public static ExerciseDto of(Exercise exercise) {
        ExerciseDto dto = ExerciseDto.builder()
                .date(exercise.getDate())
                .steps(exercise.getSteps())
                .durationMinutes(exercise.getDurationMinutes())
                .caloriesBurned(exercise.getCaloriesBurned())
                .build();

        return dto;
    }

    public Exercise toEntity(Member member) {
        return Exercise.builder()
                .date(date)
                .steps(steps)
                .durationMinutes(durationMinutes)
                .caloriesBurned(caloriesBurned)
                .member(member)
                .build();
    }
}
