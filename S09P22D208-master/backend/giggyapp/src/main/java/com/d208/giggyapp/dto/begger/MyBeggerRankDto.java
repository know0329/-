package com.d208.giggyapp.dto.begger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBeggerRankDto {
    private Long rank;
    private double ratio;
}
