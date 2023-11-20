package com.example.OpenSource.domain.comment.entity;

import com.example.OpenSource.domain.comment.dto.CommentDto;
import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import com.example.OpenSource.domain.path.domain.Path;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "comment_table")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @Column
    private int score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATH_ID")
    private Path path;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @Builder
    public Comment (Member member, Path path, String contents, int score){
        this.member = member;
        this.path = path;
        this.contents = contents;
        this.score = score;
    }

    public void update(String contents, int score){
        this.contents = contents;
        this.score = score;
    }

    public CommentResponseDto toCommentResponseDto() {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(id);
        commentResponseDto.setContents(contents);
        commentResponseDto.setMemberId(member.getId());
        commentResponseDto.setPathId(path.getId());
        commentResponseDto.setScore(score);
        return commentResponseDto;
    }
}
