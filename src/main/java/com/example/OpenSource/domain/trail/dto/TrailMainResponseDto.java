package com.example.OpenSource.domain.trail.dto;

import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.trail.domain.Trail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrailMainResponseDto {
    private Long id;
    private String wlkCoursFlagNm; // 산책 경로 구분명
    private String wlkCoursNm; // 산책 경로명
    private double averageScore;
    private int commentCount;
    private double coursSpotLa; // 위도
    private double coursSpotLo; // 경도

    public TrailMainResponseDto(Trail trail) {
        this.id = trail.getId();
        this.wlkCoursFlagNm = trail.getWlkCoursFlagNm();
        this.wlkCoursNm = trail.getWlkCoursNm();
        this.averageScore = trail.getAverageScore();
        this.commentCount = trail.getComments().size();
        this.coursSpotLa = Double.parseDouble(trail.getCoursSpotLa());
        this.coursSpotLo = Double.parseDouble(trail.getCoursSpotLo());
    }

    public TrailMainResponseDto(Path path) {
        this.id = path.getId();
        this.wlkCoursFlagNm = path.getTitle();
        this.wlkCoursNm = "(사용자 설정 게시글)";
        this.averageScore = path.getAverageScore();
        this.commentCount = path.getComments().size();
        this.coursSpotLa = path.getCoordinates().get(0).getLatitude();
        this.coursSpotLo = path.getCoordinates().get(0).getLongitude();
    }
}
