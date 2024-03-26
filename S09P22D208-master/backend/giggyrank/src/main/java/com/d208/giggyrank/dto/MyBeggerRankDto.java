package com.d208.giggyrank.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyBeggerRankDto {
    private Long rank;
    private double ratio;
}
