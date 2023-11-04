package com.example.OpenSource.domain.auth.infra.kakao;


import com.example.OpenSource.domain.auth.domain.oauth.OAuthInfoResponse;
import com.example.OpenSource.domain.auth.domain.oauth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

//https://kapi.kakao.com/v2/user/me 요청 결과

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)// 필요한 데이터만 추려내기
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount{
        private KakaoProfile profile;
        private String email;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile{
        private String nickname;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
