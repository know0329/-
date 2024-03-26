package com.d208.giggyapp.dto.begger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopBeggerRankDto {
    private String nickName;
    private double ratio;
}
