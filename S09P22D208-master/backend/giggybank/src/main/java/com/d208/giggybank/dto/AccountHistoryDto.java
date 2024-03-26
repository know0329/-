package com.d208.giggybank.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryDto {
    private int amount;
    private LocalDateTime transactionDate;
    private String transactionType;
    private int deposit;
    private int withdraw;
    private String content;

//    public static AccountHistoryDto toDto(BankAccountHistory bankAccountHistory){
//        return new AccountHistoryDto(
//                bankAccountHistory.get
//        )
//    }
}
