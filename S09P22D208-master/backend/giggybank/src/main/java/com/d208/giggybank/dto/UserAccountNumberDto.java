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
public class UserAccountNumberDto {
    private String accountNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
