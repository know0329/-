package com.d208.giggyrank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeggerRankResultDto {
    private UUID userId;
    private double ratio;
}
