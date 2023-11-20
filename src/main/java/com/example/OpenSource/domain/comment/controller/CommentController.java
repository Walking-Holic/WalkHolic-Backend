package com.example.OpenSource.domain.comment.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.comment.dto.CommentDto;
import com.example.OpenSource.domain.comment.dto.CommentResponseDto;
import com.example.OpenSource.domain.comment.entity.Comment;
import com.example.OpenSource.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/save")
    public ResponseEntity<Boolean> saveForm(){
        return ResponseEntity.ok(true);
    }
    @PostMapping("/save/{id}")
    public ResponseEntity<Boolean> save(@RequestBody @Valid CommentDto commentSaveDto, @PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.save(commentSaveDto, SecurityUtil.getCurrentMemberId(), id));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.delete(id, SecurityUtil.getCurrentMemberId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody @Valid CommentDto commentDto, @PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.update(commentDto, SecurityUtil.getCurrentMemberId(), id));
    }

    @GetMapping("/") // DB에서 전체글(Member에 따라)
    public ResponseEntity<List<CommentResponseDto>> findAll(){
        return ResponseEntity.ok(commentService.findAll(SecurityUtil.getCurrentMemberId()));
    }
}
