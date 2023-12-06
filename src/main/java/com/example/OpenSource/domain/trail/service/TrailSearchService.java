package com.example.OpenSource.domain.trail.service;

import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_DTO;

import com.example.OpenSource.domain.trail.domain.Trail;
import com.example.OpenSource.domain.trail.dto.TrailSearchResponseDto;
import com.example.OpenSource.domain.trail.repository.TrailRepository;
import com.example.OpenSource.domain.trail.repository.TrailSpecification;
import com.example.OpenSource.global.error.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrailSearchService {
    private final TrailRepository trailRepository;

    public List<TrailSearchResponseDto> searchTrailByFilters(String address, String coursLevelNm, String coursLtCn,
                                                             Pageable pageable) {
        Specification<Trail> spec = Specification.where(TrailSpecification.addressStartsWith(address))
                .and(Objects.nonNull(coursLevelNm) && !coursLevelNm.isEmpty() ? TrailSpecification.coursLevelEquals(
                        coursLevelNm) : null)
                .and(Objects.nonNull(coursLtCn) && !coursLtCn.isEmpty() ? TrailSpecification.coursLtCnEquals(coursLtCn)
                        : null);

        List<TrailSearchResponseDto> dtos = new ArrayList<>();
        Page<Trail> trails = trailRepository.findAll(spec, pageable);

        for (Trail trail : trails) {
            TrailSearchResponseDto response = Optional.of(trail)
                    .map(t -> new TrailSearchResponseDto(trail))
                    .orElseThrow(() -> new CustomException(MISMATCH_DTO));
            dtos.add(response);
        }

        return dtos;
    }
}
