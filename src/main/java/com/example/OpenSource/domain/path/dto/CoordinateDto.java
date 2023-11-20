package com.example.OpenSource.domain.path.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoordinateDto {
    private int sequence;
    private double latitude;
    private double longitude;

    @Builder
    public CoordinateDto(int sequence, double latitude, double longitude) {
        this.sequence = sequence;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
