package com.d208.giggyapp.dto.appAccountHistory;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MonthDto {
    private String month;
    private UUID userId;
}
