package com.example.OpenSource.domain.trail.repository;

import com.example.OpenSource.domain.trail.domain.Trail;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long>, JpaSpecificationExecutor<Trail> { // 동적 쿼리 생ㄲ
    List<Trail> findAll(Specification<Trail> spec);
}
