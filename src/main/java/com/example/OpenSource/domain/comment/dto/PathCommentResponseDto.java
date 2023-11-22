package com.example.OpenSource.domain.comment.dto;

import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import lombok.Getter;

@Getter
public class PathCommentResponseDto {
    private String contents;
    private int score;
    private MemberResponseDto member;

    public PathCommentResponseDto(Comment comment) {
        this.contents = comment.getContents();
        this.score = comment.getScore();
        this.member = MemberResponseDto.of(comment.getMember());
    }
}
