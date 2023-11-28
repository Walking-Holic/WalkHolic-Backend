package com.example.OpenSource.domain.auth.service;

import com.example.OpenSource.domain.auth.domain.oauth.OAuthInfoResponse;
import com.example.OpenSource.domain.auth.domain.oauth.OAuthLoginParams;
import com.example.OpenSource.domain.auth.domain.oauth.RequestOAuthInfoService;
import com.example.OpenSource.domain.auth.dto.TokenDto;
import com.example.OpenSource.domain.auth.jwt.TokenProvider;
import com.example.OpenSource.domain.member.domain.Authority;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.domain.Rank;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        String imgUrl = oAuthInfoResponse.getProfileImageUrl();
        log.info(imgUrl);
        Blob profileImageBlob = createBlobFromImageUrl(imgUrl);

        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .authority(Authority.ROLE_USER)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .rank(Rank.BRONZE)
                .build();

        member.setProfileImage(profileImageBlob);

        return memberRepository.save(member).getId();
    }

    public static Blob createBlobFromImageUrl(String imageUrl) {
        try {
            // 이미지 URL에서 파일 다운로드
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = outputStream.toByteArray();

            // Base64 인코딩
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Base64를 Blob으로 변환
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            Blob blob = new SerialBlob(decodedBytes);

            return blob;

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Error creating Blob from image URL", e);
        }
    }
}
