package com.example.OpenSource.domain.path.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.path.dto.PathAllResponseDto;
import com.example.OpenSource.domain.path.dto.PathDetailResponseDto;
import com.example.OpenSource.domain.path.dto.PathRequestDto;
import com.example.OpenSource.domain.path.service.PathService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/path")
public class PathController {
    private final PathService pathService;

    // 게시판 정보 저장
    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> savePath(@Valid @RequestPart(value = "dto") PathRequestDto pathRequestDto,
                                            @RequestPart() MultipartFile pathImage) {
        return ResponseEntity.ok(pathService.addPath(pathRequestDto, SecurityUtil.getCurrentMemberId(), pathImage));
    }

    // 모든 게시판 정보 출력
    @GetMapping(value = "")
    public List<PathAllResponseDto> getAllPaths() {
        return pathService.getAllPaths();
    }

    // 게시판 id에 해당하는 게시판 정보 출력
    @GetMapping(value = "/{pathId}")
    public ResponseEntity<PathDetailResponseDto> getPathById(@PathVariable Long pathId) {
        PathDetailResponseDto pathResponseDto = pathService.getPathResponseById(pathId);
        return ResponseEntity.ok(pathResponseDto);
    }

    @PatchMapping(value = "/update/{pathId}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> updatePath(
            @PathVariable Long pathId,
            @Valid @RequestPart(value = "dto") PathRequestDto pathRequestDto,
            @RequestPart() MultipartFile pathImage) {
        return ResponseEntity.ok(
                pathService.updatePath(pathId, pathRequestDto, SecurityUtil.getCurrentMemberId(), pathImage));
    }
}
