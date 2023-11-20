package com.example.OpenSource.domain.path.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoordinateDto {
    private int sequence;
    private double latitude;
    private double longitude;
}
