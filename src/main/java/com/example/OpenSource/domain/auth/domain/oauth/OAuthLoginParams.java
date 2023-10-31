package com.example.OpenSource.domain.auth.domain.oauth;

import org.springframework.util.MultiValueMap;

// OAuth 요청을 위한 파라미터 값들을 갖고 있는 인터페이스
// 이 interface 의 구현체는 Controller 의 @RequestBody 로도 사용하기 때문에 getXXX 를 붙이면 안된다.
public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
