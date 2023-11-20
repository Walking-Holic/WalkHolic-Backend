package com.example.OpenSource.domain.comment.service;

import com.example.OpenSource.domain.comment.dto.CommentDto;
import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.comment.repository.CommentRepository;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.path.repository.PathRepository;
import com.example.OpenSource.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.OpenSource.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PathRepository pathRepository;

    @Transactional
    public boolean save(CommentDto commentDto, Long memberId, Long pathId) {
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));
        Comment comment = commentDto.toComment(
                memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND)),
                path
        );
        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public List<CommentResponseDto> findAll(Long memberId) { // (토큰 꺼내서 해당되는 코멘트 전부 꺼내기)
        List<Comment> commentList = commentRepository.findByMemberId(memberId); // 한 게시판에 있는거 다 // 멤버 한명이 쓴거 전부
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment: commentList) {
            commentResponseDtoList.add(comment.toCommentResponseDto());
        }
        return commentResponseDtoList;
    }

    @Transactional
    public boolean delete(Long id, Long memberId) {
        if(commentRepository.getById(id).getMember().getId() != memberId){ // 작성자와 삭제자가 일치하지 않음
            new CustomException(MISMATCH_COMMENT_USERNAME);
        }
        commentRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean update(CommentDto commentDto, Long memberId, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if(commentRepository.getById(id).getMember().getId() != memberId){ // 작성자와 수정자가 일치하지 않음
            new CustomException(MISMATCH_COMMENT_USERNAME);
        }
        comment.update(commentDto.getContents(), commentDto.getScore());
        return true;
    }
}
