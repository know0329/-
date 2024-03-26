package com.d208.giggybank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionInfoDto {

    String accountNumber;
    String transactionType;
    Integer dwamount;
    String content;

}
