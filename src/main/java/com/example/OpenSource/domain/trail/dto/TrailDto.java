package com.example.OpenSource.domain.trail.dto;

import com.example.OpenSource.domain.trail.domain.Trail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrailDto {
    private String esntlId;
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
    private String coursSpotLa;
    private String coursSpotLo;

    // 생성자, getter, setter 등 필요한 메서드 추가

    // TrailDto에서 Trail 엔티티로 변환하는 메서드
    public Trail toEntity() {
        return Trail.builder()
                .esntlId(this.esntlId)
                .wlkCoursFlagNm(this.wlkCoursFlagNm)
                .wlkCoursNm(this.wlkCoursNm)
                .coursDc(this.coursDc)
                .signguNm(this.signguNm)
                .coursLevelNm(this.coursLevelNm)
                .coursLtCn(this.coursLtCn)
                .coursDetailLtCn(this.coursDetailLtCn)
                .aditDc(this.aditDc)
                .coursTimeCn(this.coursTimeCn)
                .optnDc(this.optnDc)
                .toiletDc(this.toiletDc)
                .cvntlNm(this.cvntlNm)
                .lnmAddr(this.lnmAddr)
                .coursSpotLa(this.coursSpotLa)
                .coursSpotLo(this.coursSpotLo)
                .build();
    }
}
