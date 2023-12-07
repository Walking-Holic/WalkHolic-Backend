package com.example.OpenSource.domain.trail.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.trail.dto.TrailDetailResponseDto;
import com.example.OpenSource.domain.trail.dto.TrailMainResponseDto;
import com.example.OpenSource.domain.trail.dto.TrailSearchResponseDto;
import com.example.OpenSource.domain.trail.service.TrailSearchService;
import com.example.OpenSource.domain.trail.service.TrailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trail")
public class TrailController {
    private final TrailService trailService;
    private final TrailSearchService trailSearchService;

    @GetMapping(value = "/main")
    public List<TrailMainResponseDto> getMainTrails(@RequestParam Double latitude,
                                                    @RequestParam Double longitude,
                                                    @RequestParam Double distance) {
        return trailService.listMyMap(SecurityUtil.getCurrentMemberId(), latitude, longitude, distance);
    }

    @GetMapping(value = "/search")
    public List<TrailSearchResponseDto> searchTrailsByFilters(
            @RequestParam String lnmAddr,
            @RequestParam(required = false) String coursLevelNm,
            @RequestParam(required = false) String coursLtCn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return trailSearchService.searchTrailByFilters(lnmAddr, coursLevelNm, coursLtCn, pageable);
    }

    @GetMapping(value = "/{trailId}")
    public ResponseEntity<TrailDetailResponseDto> getTrailDetail(@PathVariable Long trailId) {
        return ResponseEntity.ok().body(trailService.trailDetailDto(trailId));
    }
}
