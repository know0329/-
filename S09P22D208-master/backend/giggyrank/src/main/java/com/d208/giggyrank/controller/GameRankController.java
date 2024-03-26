package com.d208.giggyrank.controller;

import com.d208.giggyrank.dto.GameRankDto;
import com.d208.giggyrank.service.GameRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank")
public class GameRankController {
    private final GameRankService gameRankService;

    // 게임 랭킹 저장
    @PostMapping("/game/new-score")
    public ResponseEntity<String> saveGameLog(@RequestBody GameRankDto gameRankDto) {
        return gameRankService.saveScore(gameRankDto);
    }

    // 내 최고 점수 조회
    @GetMapping("/game/best-score/{userId}")
    public ResponseEntity<Integer> myBestScore(@PathVariable UUID userId) {
        return gameRankService.myBestScore(userId);
    }

    // 랭킹 top10 조회
    @GetMapping("/game/top-ten")
    public ResponseEntity<List<GameRankDto>> rankTopTen() {
        return gameRankService.topTenRank();
    }

    // 내 랭킹, 최고점수 조회
    @GetMapping("/game/my-rank/{userId}")
    public ResponseEntity<GameRankDto> checkMyRank(@PathVariable UUID userId) {
        return gameRankService.checkRank(userId);
    }
}
