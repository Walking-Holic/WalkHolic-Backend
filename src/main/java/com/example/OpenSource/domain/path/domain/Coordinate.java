package com.example.OpenSource.domain.path.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordinates")
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "path_id", nullable = false)
    private Path path;

    // 좌표의 순서
    private int sequence;

    // 위도(latitude)와 경도(longitude)
    private double latitude;

    private double longitude;

    @Builder
    public Coordinate(Path path, int sequence, double latitude, double longitude) {
        this.path = path;
        this.sequence = sequence;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
