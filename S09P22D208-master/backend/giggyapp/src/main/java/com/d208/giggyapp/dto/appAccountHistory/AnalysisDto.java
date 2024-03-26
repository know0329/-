package com.d208.giggyapp.dto.appAccountHistory;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnalysisDto {
    private String categoryName;
    private Integer price;
}
