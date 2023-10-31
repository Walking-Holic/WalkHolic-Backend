package com.example.OpenSource.domain.auth.domain.oauth;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// OAuthApiClient 를 사용하는 Service 클래스
@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    // List 로 인터페이스를 주입 받아서 Map 으로 만들면 간단히 사용 가능
    // 다음처럼 List<interface>를 주입받으면 해당 인터페이스의 구현체들이 모두 List에 담겨 온다.
    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params){
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}
