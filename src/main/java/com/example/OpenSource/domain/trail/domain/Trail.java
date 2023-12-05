package com.example.OpenSource.domain.trail.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "trail")
public class Trail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String esntlId;
    private String wlkCoursFlagNm; // 산책 경로 구분명
    private String wlkCoursNm; // 산책 경로명
    @Column(length = 4000)
    private String coursDc; // 경로 설명
    private String signguNm; // 시군구명
    private String coursLevelNm; // 경로 레벨명
    private String coursLtCn; // 경로 길이 내용
    @Column(length = 500)
    private String coursDetailLtCn; // 경로 상세 길이
    @Column(length = 4000)
    private String aditDc; // 추가 설명
    @Column(length = 500)
    private String coursTimeCn; // 경로 시간 내용
    @Column(length = 500)
    private String optnDc; // 옵션 설명
    @Column(length = 500)
    private String toiletDc; // 화장실 설명
    private String cvntlNm; // 편의시설 명
    private String lnmAddr; // 지번 주소
    private String coursSpotLa; // 위도
    private String coursSpotLo; // 경도

    private Point point;
}
