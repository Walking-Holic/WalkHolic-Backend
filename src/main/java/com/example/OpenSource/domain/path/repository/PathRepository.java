package com.example.OpenSource.domain.path.repository;

import com.example.OpenSource.domain.path.domain.Path;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathRepository extends JpaRepository<Path, Long> {
}
