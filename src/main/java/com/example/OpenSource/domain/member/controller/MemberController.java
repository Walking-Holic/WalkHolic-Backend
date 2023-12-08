package com.example.OpenSource.domain.member.controller;

import com.example.OpenSource.domain.auth.util.SecurityUtil;
import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.member.dto.UpdateRankResponse;
import com.example.OpenSource.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
    }

    @PatchMapping("/update/rank")
    public ResponseEntity<UpdateRankResponse> updateRank(
            @RequestBody MemberResponseDto dto) {
        return ResponseEntity.ok(memberService.updateRank(SecurityUtil.getCurrentMemberId(), dto));
    }
}
