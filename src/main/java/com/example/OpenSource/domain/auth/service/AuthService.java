package com.example.OpenSource.domain.auth.service;

import static com.example.OpenSource.global.error.ErrorCode.DUPLICATE_RESOURCE;
import static com.example.OpenSource.global.error.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_REFRESH_TOKEN;
import static com.example.OpenSource.global.error.ErrorCode.MISMATCH_USERNAME_OR_PASSWORD;
import static com.example.OpenSource.global.error.ErrorCode.REFRESH_TOKEN_NOT_FOUND;

import com.example.OpenSource.domain.auth.domain.RefreshToken;
import com.example.OpenSource.domain.auth.dto.LoginRequestDto;
import com.example.OpenSource.domain.auth.dto.RegisterRequestDto;
import com.example.OpenSource.domain.auth.dto.TokenDto;
import com.example.OpenSource.domain.auth.dto.TokenRequestDto;
import com.example.OpenSource.domain.auth.jwt.TokenProvider;
import com.example.OpenSource.domain.auth.repository.RefreshTokenRepository;
import com.example.OpenSource.domain.image.domain.Image;
import com.example.OpenSource.domain.image.repository.ImageRepository;
import com.example.OpenSource.domain.member.domain.Member;
import com.example.OpenSource.domain.member.repository.MemberRepository;
import com.example.OpenSource.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public boolean register(RegisterRequestDto registerRequestDto) {
        if (memberRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new CustomException(DUPLICATE_RESOURCE);
        }

        Member member = registerRequestDto.toMember(passwordEncoder);
        Image image = Image.builder()
                .imageName("default.png")
                .member(member)
                .build();

        memberRepository.save(member);
        imageRepository.save(image);
        return true;
    }

    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        try {
            // 2. 실제로 검증 (비밀번호 체크)
            // authenticate 메서드가 실행이 될 때 CustomUserDetailService 에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 jWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 발급
            return tokenDto;

        } catch (Exception e) {
            throw new CustomException(MISMATCH_USERNAME_OR_PASSWORD);
        }

    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 만료 여부 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh TOken 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomException(REFRESH_TOKEN_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(MISMATCH_REFRESH_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }
}