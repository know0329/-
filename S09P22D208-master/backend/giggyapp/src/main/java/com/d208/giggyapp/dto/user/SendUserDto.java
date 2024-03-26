package com.d208.giggyapp.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class SendUserDto {
    private UUID id;
    private String email;
    private String nickname;
    private String fcmToken;
    private String refreshToken;
    private String birthday;
    private int targetAmount;
    private int currentAmount;
    private int leftLife;
    private Long registerDate;
}
