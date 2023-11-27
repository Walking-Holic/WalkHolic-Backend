package com.example.OpenSource.domain.path.dto;

import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.path.domain.Difficulty;
import com.example.OpenSource.domain.path.domain.Path;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathRequestDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "총 거리를 입력해주세요.")
    private double totalDistance;

    @NotNull(message = "난이도를 입력해주세요.")
    private Difficulty difficulty;

    @NotEmpty(message = "총 소요시간을 입력해주세요.")
    private String estimatedTime;

    @NotEmpty(message = "경로를 설정해주세요.")
    private List<CoordinateDto> coordinates;

    public Path toPath(Member member) {
        return Path.builder()
                .title(title)
                .content(content)
                .totalDistance(totalDistance)
                .difficulty(difficulty)
                .estimatedTime(estimatedTime)
                .member(member)
                .build();
    }


}
