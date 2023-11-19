package com.example.OpenSource.domain.path.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.path.dto.PathRequestDto;
import com.example.OpenSource.domain.path.dto.PathResponseDto;
import com.example.OpenSource.domain.path.service.PathService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/save", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> savePath(@Valid @RequestPart(value = "dto") PathRequestDto pathRequestDto,
                                            @RequestPart() MultipartFile pathImage) {
        return ResponseEntity.ok(pathService.addPath(pathRequestDto, SecurityUtil.getCurrentMemberId(), pathImage));
    }

    @GetMapping(value = "")
    public List<Path> getAllPaths() {
        return pathService.getAllPaths();
    }

    @GetMapping(value = "/{pathId}")
    public ResponseEntity<PathResponseDto> getPathById(@PathVariable Long pathId) {
        PathResponseDto pathResponseDto = pathService.getPathResponseById(pathId);
        return ResponseEntity.ok(pathResponseDto);
    }
}
