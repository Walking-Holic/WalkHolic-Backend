package com.example.OpenSource.domain.exercise.repository;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMemberIdAndDateBetween(Long memberId, LocalDate startDate, LocalDate endDate);

    Exercise findByMemberIdAndDate(Long memberId, LocalDate exerciseDate);

    List<Exercise> findByMemberId(Long memberId);
}
