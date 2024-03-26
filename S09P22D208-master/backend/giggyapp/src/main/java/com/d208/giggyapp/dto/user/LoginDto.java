package com.d208.giggyapp.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String accessToken;
    private String refreshToken;
    private String fcmToken;
}
