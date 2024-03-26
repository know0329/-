package com.d208.giggyapp.dto.begger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeggerRankDto {
    private UUID userId;
    private double ratio;
}
