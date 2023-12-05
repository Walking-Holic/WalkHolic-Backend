package com.example.OpenSource.domain.trail.repository;

import com.example.OpenSource.domain.trail.domain.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailRepository extends JpaRepository<Trail, Long> {
}
