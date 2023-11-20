package com.example.OpenSource.domain.path.dto;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.path.domain.Coordinate;
import com.example.OpenSource.domain.path.domain.Difficulty;
import com.example.OpenSource.domain.path.domain.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PathResponseDto {

    private Long id;
    private String title;
    private String content;
    private double totalDistance;
    private Difficulty difficulty;
    private String estimatedTime;
    private List<CoordinateDto> coordinates;
    private MemberResponseDto member;

    public static PathResponseDto of(Path path) {
        Member member = path.getMember();

        return PathResponseDto.builder()
                .id(path.getId())
                .title(path.getTitle())
                .content(path.getContent())
                .totalDistance(path.getTotalDistance())
                .difficulty(path.getDifficulty())
                .estimatedTime(path.getEstimatedTime())
                .coordinates(mapCoordinatesToDto(path.getCoordinates()))
                .member(MemberResponseDto.of(member))
                .build();
    }

    private static List<CoordinateDto> mapCoordinatesToDto(List<Coordinate> coordinates) {
        return coordinates.stream()
                .map(coordinate -> CoordinateDto.builder()
                        .sequence(coordinate.getSequence())
                        .latitude(coordinate.getLatitude())
                        .longitude(coordinate.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }
}

