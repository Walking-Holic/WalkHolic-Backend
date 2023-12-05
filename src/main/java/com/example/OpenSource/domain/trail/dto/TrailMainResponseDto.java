package com.example.OpenSource.domain.trail.dto;

import com.example.OpenSource.domain.trail.domain.Trail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrailMainResponseDto {
    private Long id;
    private String wlkCoursFlagNm; // 산책 경로 구분명
    private String wlkCoursNm; // 산책 경로명
    // TODO: 댓글 테이블과 연결해서 평점과 댓글 수 출력

    public TrailMainResponseDto(Trail trail) {
        this.id = trail.getId();
        this.wlkCoursFlagNm = trail.getWlkCoursFlagNm();
        this.wlkCoursNm = trail.getWlkCoursNm();
    }
}
