package com.example.OpenSource.domain.image.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public Boolean upload(MultipartFile file) {
        imageService.uploadImage(file, SecurityUtil.getCurrentMemberId());
        return true;
    }
}
