package com.d208.giggyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String nickname;

    private String accessToken;

    private String refreshToken;

    private String fcmToken;

    private String accountNumber;

    private Integer targetAmount;
}
