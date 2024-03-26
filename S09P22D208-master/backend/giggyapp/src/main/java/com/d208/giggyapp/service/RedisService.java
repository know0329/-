package com.d208.giggyapp.service;

import com.d208.giggyapp.dto.user.KakaoRefreshResponseDto;
import com.d208.giggyapp.dto.user.TokenDto;
import com.d208.giggyapp.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {
    private final UserRedisRepository userRedisRepository;

    @Value("${kakao.credentials.clientId}")
    private String clientId;

    // 토큰을 레디스에 추가
    public void setAccessToken(TokenDto tokenDto) {
        userRedisRepository.save(tokenDto);
    }

    // 토큰이 레디스에 있는지 확인
    public boolean getAccessToken(String key) {
        if (userRedisRepository.findById(key).isEmpty()) return false;

        return true;
    }

    // 리프레시 토큰을 조회해서 새로 액세스 토큰 발급하기
    public String issueAccessToken(String refreshToken) {

        // 리프레시 토큰으로 카카오 서버와 연동해서 accessToken 발급받기
        String url = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;");

        // 요청을 보낼 데이터 생성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", clientId); // 환경 변수로 빼야함
        requestBody.add("refresh_token", refreshToken);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<KakaoRefreshResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, KakaoRefreshResponseDto.class);
        KakaoRefreshResponseDto kakaoRefreshResponseDto = response.getBody();
        String accessToken = kakaoRefreshResponseDto.getAccessToken();

        // 레디스에 토큰 저장
        this.setAccessToken(TokenDto.builder()
                .accessToken(accessToken)
                .exist(1)
                .build());


        // 클라이언트로 내려주기
        return accessToken;
    }
}
