package com.example.OpenSource.domain.auth.exception;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
    JwtExceptionFilter 를 통해 여기까지 오기 전에 미리 JWTException 를 일으켜서
    JwtExceptionFilter 가 그 Exception 을 잡아서 처리 한 뒤, 반환하기에 필요없는 클래스
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증 예외가 생김 - 401 error
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
