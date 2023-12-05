package com.example.OpenSource.domain.trail.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.trail.dto.TrailMainResponseDto;
import com.example.OpenSource.domain.trail.service.TrailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trail")
public class TrailController {
    private final TrailService trailService;

    @GetMapping(value = "/main")
    public List<TrailMainResponseDto> getMainTrails(@RequestParam Double latitude,
                                                    @RequestParam Double longitude,
                                                    @RequestParam Double distance) {
        return trailService.listMyMap(SecurityUtil.getCurrentMemberId(), latitude, longitude, distance);
    }
}
