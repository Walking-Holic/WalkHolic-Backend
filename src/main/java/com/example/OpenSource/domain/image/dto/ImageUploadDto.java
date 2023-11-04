package com.example.OpenSource.domain.image.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {
    private MultipartFile file;
}
