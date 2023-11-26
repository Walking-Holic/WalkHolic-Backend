package com.example.OpenSource.domain.path.dto;

import com.example.OpenSource.domain.comment.dto.PathCommentResponseDto;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.path.domain.Coordinate;
import com.example.OpenSource.domain.path.domain.Difficulty;
import com.example.OpenSource.domain.path.domain.Path;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PathDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private double totalDistance;
    private Difficulty difficulty;
    private String estimatedTime;
    private double averageScore;
    private byte[] pathImage;
    private List<CoordinateDto> coordinates;
    private MemberResponseDto member;
    private List<PathCommentResponseDto> comments;

    public static PathDetailResponseDto of(Path path, List<PathCommentResponseDto> comments) {
        Member member = path.getMember();

        return PathDetailResponseDto.builder()
                .id(path.getId())
                .title(path.getTitle())
                .content(path.getContent())
                .totalDistance(path.getTotalDistance())
                .difficulty(path.getDifficulty())
                .estimatedTime(path.getEstimatedTime())
                .averageScore(path.getAverageScore())
                .pathImage(declareChannelImage(path.getPathImage()))
                .coordinates(mapCoordinatesToDto(path.getCoordinates()))
                .member(MemberResponseDto.of(member))
                .comments(comments)
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

    public static byte[] declareChannelImage(Blob pathImage) {
        if (pathImage != null) {
            try {
                return pathImage.getBytes(1, (int) pathImage.length());
            } catch (SQLException e) {
                // 예외 처리
            }
        }
        return new byte[0];
    }
}

