package com.example.OpenSource.domain.trail.dto;

import com.example.OpenSource.domain.trail.domain.Trail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrailSearchResponseDto {
    private Long id;
    private String signgu_nm; // SIGNGU_NM
    private String wlkCoursFlagNm;
    private String wlkCoursNm;
    private String coursLevelNm;
    private String coursDetailLtCn;
    private String coursTimeCn;
    private double averageScore;
    private int commentCount;

    public TrailSearchResponseDto(Trail trail) {
        this.id = trail.getId();
        this.signgu_nm = trail.getSignguNm();
        this.wlkCoursFlagNm = trail.getWlkCoursFlagNm();
        this.wlkCoursNm = trail.getWlkCoursNm();
        this.coursLevelNm = trail.getCoursLevelNm();
        this.coursDetailLtCn = trail.getCoursDetailLtCn();
        this.coursTimeCn = trail.getCoursTimeCn();
        this.averageScore = trail.getAverageScore();
        this.commentCount = trail.getComments().size();
    }

}
