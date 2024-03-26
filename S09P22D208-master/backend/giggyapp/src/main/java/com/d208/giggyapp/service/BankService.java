package com.d208.giggyapp.service;

import com.d208.giggyapp.dto.Auth1RequestDto;
import com.d208.giggyapp.dto.Auth1ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BankService {
    private final UserService userService;

    @Value("${server.url.bank}")
    private String bankBaseUrl;

    public Auth1ResponseDto oneAuthRequest(Auth1RequestDto auth1RequestDto) {
        // 은행에다가 해당 정보를 전달한다.
        String url = bankBaseUrl + "/auth";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> dataBody = new HashMap<>();

        dataBody.put("accountNumber", auth1RequestDto.getAccountNumber());
        dataBody.put("birthday", auth1RequestDto.getBirthday());

        // HTTP 요청 엔터티 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataBody, null);

        // RestTemplate을 사용하여 HTTP POST 요청 보내기
        ResponseEntity<Auth1ResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Auth1ResponseDto.class);

        // 응답 처리
        Auth1ResponseDto auth1ResponseDto = responseEntity.getBody();

        return auth1ResponseDto;
    }

}
