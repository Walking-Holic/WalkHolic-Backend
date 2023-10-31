package com.example.OpenSource.domain.auth.domain.oauth;

public interface OAuthApiClient {
    // Client 의 타입 반환
    OAuthProvider oAuthProvider();

    // Authorization Code 를 기반으로 인증 API를 요청해서 Access Token을 획득
    String requestAccessToken(OAuthLoginParams params);

    // Access Token 을 기반으로 Email, Nickname 이 포함된 프로필 정보 획득
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
