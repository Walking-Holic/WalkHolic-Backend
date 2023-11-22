package com.example.OpenSource.domain.comment.dto;

import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.member.domain.Member;
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
public class CommentResponseDto {
    private Long id;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, message = "내용은 1자 이상이어야 합니다.")
    private String contents;
    @NotBlank(message = "평점을 선택해주세요.")
    private int score;

    @NotBlank
    private Long pathId;

    public Comment toComment(Member member) {
        return Comment.builder()
                .contents(contents)
                .score(score)
                .member(member)
                .build();
    }
}
