package com.d208.giggyrank.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BeggerRankDto {
    private UUID userId;
    private int targetAmount;
    private int currentAmount;
}
