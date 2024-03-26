package com.d208.giggyapp.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Auth1MobileDto {
    private int type;
    private String content;
}
