package com.example.OpenSource.domain.trail.dto;

import com.example.OpenSource.domain.comment.dto.PathCommentResponseDto;
import com.example.OpenSource.domain.trail.domain.Trail;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrailDetailResponseDto {
    private String wlkCoursFlagNm;
    private String wlkCoursNm;
    private String coursDc;
    private String signguNm;
    private String coursLevelNm;
    private String coursLtCn;
    private String coursDetailLtCn;
    private String aditDc;
    private String coursTimeCn;
    private String optnDc;
    private String toiletDc;
    private String cvntlNm;
    private String lnmAddr;
    private double averageScore;
    private int commentCount;
    private List<PathCommentResponseDto> comments;

    public TrailDetailResponseDto(Trail trail, List<PathCommentResponseDto> comments) {
        this.wlkCoursFlagNm = trail.getWlkCoursFlagNm();
        this.wlkCoursNm = trail.getWlkCoursNm();
        this.coursDc = trail.getCoursDc();
        this.aditDc = trail.getAditDc();
        this.signguNm = trail.getSignguNm();
        this.coursLevelNm = trail.getCoursLevelNm();
        this.coursLtCn = trail.getCoursLtCn();
        this.coursDetailLtCn = trail.getCoursDetailLtCn();
        this.coursTimeCn = trail.getCoursTimeCn();
        this.optnDc = trail.getOptnDc();
        this.toiletDc = trail.getToiletDc();
        this.cvntlNm = trail.getCvntlNm();
        this.lnmAddr = trail.getLnmAddr();
        this.averageScore = trail.getAverageScore();
        this.commentCount = trail.getComments().size();
        this.comments = comments;
    }
}
