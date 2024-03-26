package com.d208.giggyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Auth1RequestDto {
    private String accountNumber;

    private String fcmToken;

    private String birthday;
}
