package com.d208.giggyapp.dto.appAccountHistory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountHistoryDto {
    private Long id;
    private int amount;
    private String content;
    private Long transactionDate;
    private String transactionType;
    private String category;
    private int deposit;
    private int withdraw;
}
