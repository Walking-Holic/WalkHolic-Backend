package com.example.OpenSource.domain.comment.entity;

import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.path.domain.Path;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    public Comment(Member member, Path path, String contents, int score) {
        this.member = member;
        this.path = path;
        this.contents = contents;
        this.score = score;
    }

    public void update(String contents, int score) {
        this.contents = contents;
        this.score = score;
    }

    public CommentResponseDto toCommentResponseDto() {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(id);
        commentResponseDto.setContents(contents);
        commentResponseDto.setPathId(path.getId());
        commentResponseDto.setScore(score);
        return commentResponseDto;
    }
}
