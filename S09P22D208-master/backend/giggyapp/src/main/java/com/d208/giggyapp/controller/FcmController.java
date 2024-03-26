package com.d208.giggyapp.controller;

import com.d208.giggyapp.dto.FcmDto;
import com.d208.giggyapp.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class FcmController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/fcm")
    public void sendFcm(@RequestBody FcmDto fcmDto) throws IOException {
        String token = fcmDto.getMessage().getToken();
        String title = fcmDto.getMessage().getData().getTitle();
        String body = fcmDto.getMessage().getData().getBody();

        firebaseCloudMessageService.sendMessageTo(token, title, body);
    }
}

