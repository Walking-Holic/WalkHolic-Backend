package com.example.OpenSource.domain.path.dto;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.path.domain.Difficulty;
import com.example.OpenSource.domain.path.domain.Path;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PathAllResponseDto {
    private Long id;
    private String title;
    private String content;
    private double totalDistance;
    private Difficulty difficulty;
    private String estimatedTime;
    private MemberResponseDto member;

    public static PathAllResponseDto of(Path path) {
        Member member = path.getMember();

        return PathAllResponseDto.builder()
                .id(path.getId())
                .title(path.getTitle())
                .content(path.getContent())
                .totalDistance(path.getTotalDistance())
                .difficulty(path.getDifficulty())
                .estimatedTime(path.getEstimatedTime())
                .member(MemberResponseDto.of(member))
                .build();
    }
}
