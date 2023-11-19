package com.example.OpenSource.domain.path.repository;

import com.example.OpenSource.domain.path.domain.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
}
