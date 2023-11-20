package com.example.OpenSource.domain.comment.dto;

import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.path.domain.Path;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, message = "내용은 1자 이상이어야 합니다.")
    private String contents;

    @Min(value = 1, message = "1 이상의 값을 입력해주세요.")
    @Max(value = 5, message = "5 이하의 값을 입력해주세요.")
    private int score;

    private Long pathId;

    public Comment toComment(Member member, Path path){
        return Comment.builder()
                .contents(contents)
                .score(score)
                .path(path)
                .member(member)
                .build();
    }
}
