package com.d208.giggyapp.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoRefreshResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token_expires_in")
    private long refreshTokenExpiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("id_token")
    private String idToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;
}
