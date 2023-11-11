package com.example.OpenSource.domain.auth.util;

import static com.example.OpenSource.global.error.ErrorCode.INVALID_CONTEXT;

import com.example.OpenSource.global.error.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
    }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomException(INVALID_CONTEXT);
        }

        return Long.parseLong(authentication.getName());
    }
}
