package com.example.OpenSource.domain.member.domain;

import com.example.OpenSource.domain.auth.domain.oauth.OAuthProvider;
import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.path.domain.Path;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    private int walk;

    private int time;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Lob
    @JsonIgnore
    private Blob profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Path> paths = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "collection",
            joinColumns = @JoinColumn(name = "path_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Path> pathCollections = new HashSet<>();

    public boolean isCollections(Path path) {
        if (path == null) {
            return false;
        }
        return this.pathCollections.contains(path);
    }

    @Builder
    public Member(String email, String password, String nickname, String name, int walk, Authority authority,
                  OAuthProvider oAuthProvider, Rank rank) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.walk = walk;
        this.authority = authority;
        this.oAuthProvider = oAuthProvider;
        this.rank = rank;
    }

    public void setProfileImage(Blob imageFile) {
        this.profileImage = imageFile;
    }

    public void update(String nickname, String name) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (name != null) {
            this.name = name;
        }
    }

    public void addPaths(Path path) {
        paths.add(path);
        path.setMember(this);
    }

    public void removePaths(Path path) {
        paths.remove(path);
    }

    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setMember(this);
    }

    public void removeComments(Comment comment) {
        comments.remove(comment);
    }

    public void addPathCollection(Path path) {
        this.pathCollections.add(path);
    }

    public void removePathCollection(Path path) {
        this.pathCollections.remove(path);
    }

    public Set<Path> getCollectedPaths() {
        return pathCollections;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
