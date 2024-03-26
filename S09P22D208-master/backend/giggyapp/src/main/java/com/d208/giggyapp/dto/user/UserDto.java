package com.d208.giggyapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String nickname;
    private String fcmToken;
    private String refreshToken;
    private String birthday;
    private int targetAmount;
    private int currentAmount;
    private int leftLife;
    private LocalDateTime registerDate;
}
