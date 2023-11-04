package com.example.OpenSource.domain.auth.jwt.filter;

import com.example.OpenSource.global.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터를 통해 따로 잡아냄.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (JwtException ex) {
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            setErrorResponse(request, response, ex);
        }
    }

    // ErrorResponse 형태로 출력되는 JSON
    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, JwtException ex) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .message(ex.getMessage())
                .build()));
    }
}
