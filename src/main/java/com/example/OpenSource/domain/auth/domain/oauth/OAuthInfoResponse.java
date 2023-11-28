package com.example.OpenSource.domain.auth.domain.oauth;

public interface OAuthInfoResponse {
    String getEmail();

    String getNickname();

    OAuthProvider getOAuthProvider();

    String getProfileImageUrl();
}
