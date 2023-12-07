package com.example.OpenSource.domain.exercise.domain;

import com.example.OpenSource.domain.exercise.dto.ExerciseDto;
import com.example.OpenSource.domain.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int steps;
    private int durationMinutes;
    private int caloriesBurned;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(ExerciseDto exerciseDto) {
        this.steps += exerciseDto.getSteps();
        this.durationMinutes += exerciseDto.getDurationMinutes();
        this.caloriesBurned += exerciseDto.getCaloriesBurned();
    }
}
