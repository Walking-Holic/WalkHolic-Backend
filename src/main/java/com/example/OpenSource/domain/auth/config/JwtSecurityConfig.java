package com.example.OpenSource.domain.auth.config;

import com.example.OpenSource.domain.auth.jwt.TokenProvider;
import com.example.OpenSource.domain.auth.jwt.filter.JwtExceptionFilter;
import com.example.OpenSource.domain.auth.jwt.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TokenProvide 와 JwtFilter 를 SecurityConfig 에 적용할 때 사용
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

    // TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        // jwt 관련 오류를 잡아내는 필터를 앞에 붙여서 jwt 검사부터 함.
        builder.addFilterBefore(jwtExceptionFilter,customFilter.getClass());
    }
}
