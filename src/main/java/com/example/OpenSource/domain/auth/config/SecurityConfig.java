package com.example.OpenSource.domain.auth.config;

import com.example.OpenSource.domain.auth.exception.JwtAccessDeniedHandler;
import com.example.OpenSource.domain.auth.exception.JwtAuthenticationEntryPoint;
import com.example.OpenSource.domain.auth.jwt.TokenProvider;
import com.example.OpenSource.domain.auth.jwt.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.OpenSource.domain.member.domain.Authority.ROLE_ADMIN;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // CSRF 설정 Disable
        http.csrf(AbstractHttpConfigurer::disable)

                // exception handling 할 때 우리가 만든 클래스 추가
//                .exceptionHandling(authenticationManager -> authenticationManager
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // 세션 설정을 Stateless 로 변경
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAnyAuthority(String.valueOf(ROLE_ADMIN))
                        .anyRequest().authenticated()) //나머지 API는 모두 인증 필요


                .apply(new JwtSecurityConfig(tokenProvider, jwtExceptionFilter));

        return http.build();
    }
}
