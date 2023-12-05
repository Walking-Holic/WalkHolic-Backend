package com.example.OpenSource.domain.comment.service;

import static com.example.OpenSource.global.error.ErrorCode.COMMENT_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_COMMENT_USERNAME;
import static com.example.OpenSource.global.error.ErrorCode.PATH_NOT_FOUND;

import com.example.OpenSource.domain.comment.dto.CommentDto;
import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.comment.repository.CommentRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.domain.path.domain.Path;
import com.example.OpenSource.domain.path.repository.PathRepository;
import com.example.OpenSource.domain.trail.domain.Trail;
import com.example.OpenSource.domain.trail.repository.TrailRepository;
import com.example.OpenSource.global.error.CustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PathRepository pathRepository;
    private final TrailRepository trailRepository;

    @Transactional
    public boolean save(CommentDto commentDto, Long memberId, Long pathId) {
        Path path = pathRepository.findById(pathId).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = commentDto.toComment(member, path);

        member.addComments(comment); // 멤버에 댓글 추가
        path.addComments(comment); // 게시판에 댓글 추가

        commentRepository.save(comment);
        updateAverageScoreComment(path); // 게시판 평균 평점 업데이트
        return true;
    }

    // TODO: 댓글 작성, 수정 시, 삭제 시 게시판의 평균 평점 수정
    // 게시판의 평균 평점을 수정하는 함수
    public void updateAverageScoreComment(Path path) {
        List<Comment> comments = path.getComments();
        double averageScore;

        if (comments != null && !comments.isEmpty()) {
            int totalScore = 0;
            for (Comment comment : comments) {
                totalScore += comment.getScore();
            }

            averageScore = (double) totalScore / comments.size();
        } else {
            averageScore = 0.0;
        }
        log.info(String.valueOf(averageScore));
        path.setAverageScore(averageScore);
    }

    @Transactional
    public List<CommentResponseDto> findAll(Long memberId) { // (토큰 꺼내서 해당되는 코멘트 전부 꺼내기)
        List<Comment> commentList = commentRepository.findByMemberId(memberId); // 한 게시판에 있는거 다 // 멤버 한명이 쓴거 전부
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(comment.toCommentResponseDto());
        }
        return commentResponseDtoList;
    }

    @Transactional
    public boolean delete(Long id, Long memberId) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if (commentRepository.getById(id).getMember().getId() != memberId) { // 작성자와 삭제자가 일치하지 않음
            throw new CustomException(MISMATCH_COMMENT_USERNAME);
        }
        commentRepository.deleteById(id);
        comment.getMember().removeComments(comment);
        comment.getPath().removeComments(comment);

        updateAverageScoreComment(comment.getPath());
        return true;
    }

    @Transactional
    public boolean update(CommentDto commentDto, Long memberId, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if (commentRepository.getById(id).getMember().getId() != memberId) { // 작성자와 수정자가 일치하지 않음
            throw new CustomException(MISMATCH_COMMENT_USERNAME);
        }
        comment.update(commentDto.getContents(), commentDto.getScore());
        updateAverageScoreComment(comment.getPath());
        return true;
    }

    /*
     * trail에 대한 댓글 기능
     */

    public Boolean saveTrailComment(CommentDto commentDto, Long memberId, Long id) {
        Trail trail = trailRepository.findById(id).orElseThrow(() -> new CustomException(PATH_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Comment comment = commentDto.toComment(member, trail);

        member.addComments(comment); // 멤버에 댓글 추가
        trail.addComments(comment); // 게시판에 댓글 추가

        commentRepository.save(comment);
        updateAverageScoreComment(trail); // 게시판 평균 평점 업데이트
        return true;
    }

    public void updateAverageScoreComment(Trail trail) {
        List<Comment> comments = trail.getComments();
        double averageScore;

        if (comments != null && !comments.isEmpty()) {
            int totalScore = 0;
            for (Comment comment : comments) {
                totalScore += comment.getScore();
            }

            averageScore = (double) totalScore / comments.size();
        } else {
            averageScore = 0.0;
        }
        log.info(String.valueOf(averageScore));
        trail.setAverageScore(averageScore);
    }

    public Boolean deleteTrailComment(Long id, Long memberId) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if (commentRepository.getById(id).getMember().getId() != memberId) { // 작성자와 삭제자가 일치하지 않음
            throw new CustomException(MISMATCH_COMMENT_USERNAME);
        }
        commentRepository.deleteById(id);
        comment.getMember().removeComments(comment);
        comment.getTrail().removeComments(comment);

        updateAverageScoreComment(comment.getTrail());
        return true;
    }

    public Boolean updateTrailComment(CommentDto commentDto, Long memberId, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
        if (commentRepository.getById(id).getMember().getId() != memberId) { // 작성자와 수정자가 일치하지 않음
            throw new CustomException(MISMATCH_COMMENT_USERNAME);
        }

        comment.update(commentDto.getContents(), commentDto.getScore());
        commentRepository.save(comment);
        updateAverageScoreComment(comment.getTrail());
        return true;
    }
}
