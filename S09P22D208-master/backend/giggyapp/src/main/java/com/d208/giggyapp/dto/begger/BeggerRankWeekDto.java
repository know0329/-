package com.d208.giggyapp.dto.begger;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BeggerRankWeekDto {
    private UUID userId;
    private double ratio;
    private int round;
}
