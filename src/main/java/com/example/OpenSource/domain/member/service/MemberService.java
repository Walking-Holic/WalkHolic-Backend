package com.example.OpenSource.domain.member.service;

import static com.example.OpenSource.global.error.ErrorCode.MEMBER_NOT_FOUND;

import com.example.OpenSource.domain.member.dto.MemberResponseDto;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponseDto::of) //toDto
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    public MemberResponseDto findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}
