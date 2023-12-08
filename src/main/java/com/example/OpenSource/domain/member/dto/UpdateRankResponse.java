package com.example.OpenSource.domain.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRankResponse {
    private LocalDate date;
    private int steps;
    private int durationMinutes;
    private int caloriesBurned;
    private boolean isUpdated;
}
