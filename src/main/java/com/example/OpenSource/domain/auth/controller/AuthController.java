package com.example.OpenSource.domain.auth.controller;

import com.example.OpenSource.domain.auth.dto.LoginRequestDto;
import com.example.OpenSource.domain.auth.dto.RegisterRequestDto;
import com.example.OpenSource.domain.auth.dto.TokenDto;
import com.example.OpenSource.domain.auth.dto.TokenRequestDto;
import com.example.OpenSource.domain.auth.dto.UpdateRequestDto;
import com.example.OpenSource.domain.auth.infra.kakao.KakaoLoginParams;
import com.example.OpenSource.domain.auth.infra.naver.NaverLoginParams;
import com.example.OpenSource.domain.auth.service.AuthService;
import com.example.OpenSource.domain.auth.service.OAuthLoginService;
import com.example.OpenSource.domain.auth.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final OAuthLoginService oAuthLoginService;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> register(@Valid @RequestPart(value = "dto") RegisterRequestDto registerRequestDto,
                                            @RequestPart(required = false) MultipartFile profileImage) {
        return ResponseEntity.ok(authService.register(registerRequestDto, profileImage));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    public ResponseEntity<TokenDto> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> updateMember(
            @Valid @RequestPart(value = "dto") UpdateRequestDto updateRequestDto,
            @RequestPart(required = false) MultipartFile profileImage) {
        return ResponseEntity.ok(
                authService.updateMember(SecurityUtil.getCurrentMemberId(), updateRequestDto, profileImage));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteMember() {
        authService.deleteMember(SecurityUtil.getCurrentMemberId());
        return ResponseEntity.status(HttpStatus.OK).body("삭제 성공!");
    }
}
