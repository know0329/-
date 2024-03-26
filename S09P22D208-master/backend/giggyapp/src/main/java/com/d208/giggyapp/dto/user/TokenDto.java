package com.d208.giggyapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "ACCESSTOKEN", timeToLive = 60*60*12)
public class TokenDto {
    @Id
    private String accessToken;

    private int exist;
}
