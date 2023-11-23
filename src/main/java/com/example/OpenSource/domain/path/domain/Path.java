package com.example.OpenSource.domain.path.domain;

import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Blob;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "path")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id", updatable = false)
    private Long id;

    private String title;

    private String content;

    // 총 길이
    private double totalDistance;

    // 난이도 : 상, 중, 하
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    // 총 소요 시간
    private String estimatedTime;

    // 게시판의 평점의 평균
    private double averageScore;

    @Lob
    @JsonIgnore
    private Blob pathImage;

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coordinate> coordinates;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public void setCoordinates(List<Coordinate> savedCoordinates) {
        this.coordinates = savedCoordinates;
    }

    public void setPathImage(Blob pathImage) {
        this.pathImage = pathImage;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setPath(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
    }
}
