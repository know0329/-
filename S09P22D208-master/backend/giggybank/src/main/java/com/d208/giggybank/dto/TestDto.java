package com.d208.giggybank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private String name;

    private String birthday;

    private String accountNumber;

    private int balance;
}
