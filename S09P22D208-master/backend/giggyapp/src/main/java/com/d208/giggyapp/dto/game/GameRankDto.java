package com.d208.giggyapp.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRankDto {

    private UUID userId;

    private int score;
    private int rank;
    private String nickname;

}
