package com.example.OpenSource.domain.auth.service;

import static com.example.OpenSource.domain.auth.service.AuthService.getDefaultProfileImage;

import com.example.OpenSource.domain.auth.domain.oauth.OAuthInfoResponse;
import com.example.OpenSource.domain.auth.domain.oauth.OAuthLoginParams;
import com.example.OpenSource.domain.auth.domain.oauth.RequestOAuthInfoService;
import com.example.OpenSource.domain.auth.dto.TokenDto;
import com.example.OpenSource.domain.auth.jwt.TokenProvider;
import com.example.OpenSource.domain.member.domain.Authority;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenDto login(OAuthLoginParams params) {
        // 소셜 OAuth 인증 후 프로필 정보 가져오기
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

        return tokenProvider.generateTokenDto(memberId); //Access Token 생성 후 반환
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        // email 정보로 사용자 확인
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));// 없으면 새로 가입처리
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .authority(Authority.ROLE_USER)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .rank(Rank.BRONZE)
                .build();

        member.setProfileImage(getDefaultProfileImage());

        return memberRepository.save(member).getId();
    }
}
