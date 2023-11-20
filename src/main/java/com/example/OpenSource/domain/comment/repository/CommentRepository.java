package com.example.OpenSource.domain.comment.repository;

import com.example.OpenSource.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMemberId(Long memberId);
}
