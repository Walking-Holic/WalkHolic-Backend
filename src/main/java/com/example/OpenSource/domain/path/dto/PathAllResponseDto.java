package com.example.OpenSource.domain.path.dto;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.path.domain.Difficulty;
import com.example.OpenSource.domain.path.domain.Path;
import java.sql.Blob;
import java.sql.SQLException;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PathAllResponseDto {
    private Long id;
    private String title;
    private double totalDistance;
    private Difficulty difficulty;
    private String estimatedTime;
    private double averageScore;
    private int commentCount;
    private byte[] pathImage;
    private MemberResponseDto member;

    public PathAllResponseDto(Path path) {
        Member member = path.getMember();
        this.id = path.getId();
        this.title = path.getTitle();
        this.totalDistance = path.getTotalDistance();
        this.difficulty = path.getDifficulty();
        this.estimatedTime = path.getEstimatedTime();
        this.averageScore = path.getAverageScore();
        this.commentCount = path.getComments().size();
        declarePathImage(path.getPathImage());
        this.member = MemberResponseDto.of(member);
    }

    public void declarePathImage(Blob pathImage) {
        if (pathImage != null) {
            try {
                this.pathImage = pathImage.getBytes(1, (int) pathImage.length());
            } catch (SQLException e) {
                // 예외 처리
            }
        }
    }
}
