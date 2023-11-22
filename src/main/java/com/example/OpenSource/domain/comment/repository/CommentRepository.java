package com.example.OpenSource.domain.comment.repository;

import com.example.OpenSource.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMemberId(Long memberId);

    List<Comment> findByPathId(Long pathId);
}
