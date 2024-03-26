package com.d208.giggyapp.dto.appAccountHistory;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private String accountNumber;
    private UUID userId;
}
