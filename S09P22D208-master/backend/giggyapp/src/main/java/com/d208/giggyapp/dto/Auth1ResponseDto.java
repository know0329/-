package com.d208.giggyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class Auth1ResponseDto {
    private int amount;

    private LocalDateTime transactionDate;

    private String transactionType;

    private int deposit;

    private int withdraw;

    private String content;
}
