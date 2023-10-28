// 박지현 - 소셜 로그인 (카카오) 백엔드 연결 코드를 팀원의 코드를 참고 & 수정하여 구현하였습니다.
// application-oauth.properties 에 카카오로그인 registration 와 provider 설정 완료

package com.momento.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        // Kakao에서 가져온 사용자 정보를 저장하는 생성자
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        // OAuth2UserInfo 인터페이스에서 제공하는 메서드 구현
        // 사용자 고유 식별자 (Provider-specific ID)를 반환합니다.
        // Long 타입이기 때문에 toString()으로 형으로 변환합니다.
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        // OAuth2UserInfo 인터페이스에서 제공하는 메서드 구현
        // 사용자 정보 제공자 (여기서는 "kakao"로 고정)
        return "kakao";
    }

    @Override
    public String getEmail() {
        // OAuth2UserInfo 인터페이스에서 제공하는 메서드 구현
        // 사용자 이메일 주소를 반환합니다.
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        // OAuth2UserInfo 인터페이스에서 제공하는 메서드 구현
        // 사용자 이름을 반환합니다.
        return (String) attributes.get("name");
    }
}