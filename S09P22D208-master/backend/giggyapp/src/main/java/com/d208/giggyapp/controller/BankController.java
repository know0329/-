package com.d208.giggyapp.controller;

import com.d208.giggyapp.dto.Auth1MobileDto;
import com.d208.giggyapp.dto.Auth1RequestDto;
import com.d208.giggyapp.dto.Auth1ResponseDto;
import com.d208.giggyapp.repository.UserRepository;
import com.d208.giggyapp.service.BankService;
import com.d208.giggyapp.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class BankController {
    private final BankService bankService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    private final UserRepository userRepository;

    // 1원 인증
    @PostMapping("/bank/auth")
    public ResponseEntity<Auth1MobileDto> oneAuth(@RequestBody Auth1RequestDto auth1RequestDto) throws IOException {
        // 은행에 1원 인증 요청해서 응답을 받는다.
        Auth1ResponseDto auth1ResponseDto = bankService.oneAuthRequest(auth1RequestDto);

        if (auth1ResponseDto.getContent() == null) return ResponseEntity.ok(Auth1MobileDto.builder()
                .type(1)
                .content("")
                .build());

        // FCM
        // 서비스로 분리해야함.
        String token = auth1RequestDto.getFcmToken();
        String title = "1 원 입 금";
        String body = auth1ResponseDto.getContent();
        firebaseCloudMessageService.sendMessageTo(token, title, body);

        return ResponseEntity.ok(Auth1MobileDto.builder()
                .type(2)
                .content(body)
                .build());
    }
}
