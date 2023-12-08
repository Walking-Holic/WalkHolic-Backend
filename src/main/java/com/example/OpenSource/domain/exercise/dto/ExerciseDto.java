package com.example.OpenSource.domain.exercise.dto;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import com.example.OpenSource.domain.member.domain.Member;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public ExerciseSummaryDto toSummaryDto() {
        ExerciseSummaryDto summaryDto = new ExerciseSummaryDto();
        summaryDto.setSteps(this.steps);
        summaryDto.setDurationMinutes(this.durationMinutes);
        summaryDto.setCaloriesBurned(this.caloriesBurned);
        // 다른 필요한 필드들도 추가

        return summaryDto;
    }
}
