package com.d208.giggybank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private int amount;
    private LocalDateTime transactionDate;

    private String transactionType;

    private int deposit;

    private int withdraw;

    private String content;

}
