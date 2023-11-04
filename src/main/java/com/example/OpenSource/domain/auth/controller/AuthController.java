package com.example.OpenSource.domain.auth.controller;

import com.example.OpenSource.domain.auth.dto.LoginRequestDto;
import com.example.OpenSource.domain.auth.dto.RegisterRequestDto;
import com.example.OpenSource.domain.auth.dto.TokenDto;
import com.example.OpenSource.domain.auth.dto.TokenRequestDto;
import com.example.OpenSource.domain.auth.infra.kakao.KakaoLoginParams;
import com.example.OpenSource.domain.auth.infra.naver.NaverLoginParams;
import com.example.OpenSource.domain.auth.service.AuthService;
import com.example.OpenSource.domain.auth.service.OAuthLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> loginKakao(@RequestBody KakaoLoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    public ResponseEntity<TokenDto> loginNaver(@RequestBody NaverLoginParams params){
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

}
