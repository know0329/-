package com.d208.giggyrank.domain.begger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallOfBegger {
    private Long id;

    private int season;
    private UUID userId;

}
