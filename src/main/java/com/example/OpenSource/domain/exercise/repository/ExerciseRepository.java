package com.example.OpenSource.domain.exercise.repository;

import com.example.OpenSource.domain.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
