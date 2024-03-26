package com.d208.giggyrank.service;

import com.d208.giggyrank.dto.GameRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameRankService {

    private final ZSetOperations<String, String> zsetOps;
    @Autowired
    public GameRankService(RedisTemplate<String, String> redisTemplate) {
        this.zsetOps = redisTemplate.opsForZSet();
    }

    // 점수 저장
    @Transactional
    public ResponseEntity<String> saveScore(GameRankDto gameRankDto) {

        // Redis 서버에 각 유저의 점수를 저장하는 로직
        // 점수를 불러올 때 해당 유저의 현재 저장된 있는 점수와 비교해서 새로운 점수가 더 크다면 바꿔서 저장
        // 아니라면 그냥 스킵
        UUID userId = gameRankDto.getUserId();
        int newScore = (int) gameRankDto.getScore();

        // 게임 랭킹에 기존 랭킹이 있는지 확인
        // 없으면 바로 저장해주고 있으면 비교해서 높은 점수를 저장
        String userIdStr = userId.toString();
        Double currentScore = zsetOps.score("GameRank", userIdStr);

        if (currentScore == null || newScore > currentScore) {
            zsetOps.add("GameRank", userIdStr, newScore);
            return ResponseEntity.ok("새로운 기록 등록");
        } else {
            return ResponseEntity.ok("이전의 기록보다 낮음");
        }
    }

//    // 내 랭킹 불러오기
//    @Transactional
//    public ResponseEntity<Integer> checkRank(UUID userId) {
//        String userIdStr = userId.toString();
//
//        Long rank = zsetOps.reverseRank("GameRank", userIdStr);
//        if (rank == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
//        } else {
//            return ResponseEntity.ok(rank.intValue() + 1);
//        }
//    }

    // 내 랭킹 + 최고 점수 불러오기
    @Transactional
    public ResponseEntity<GameRankDto> checkRank(UUID userId) {
        String userIdStr = userId.toString();

        Long rank = zsetOps.reverseRank("GameRank", userIdStr);
        Double score = zsetOps.score("GameRank", userIdStr);

        if (rank == null || score == null) {
            GameRankDto gameRankDto = new GameRankDto(userId, 0, zsetOps.size("GameRank").intValue() + 1);
            return ResponseEntity.ok(gameRankDto);
        } else {
            GameRankDto gameRankDto = new GameRankDto(userId, score.intValue(), rank.intValue() + 1);
            return ResponseEntity.ok(gameRankDto);
        }
    }

    // 내 최고 점수 불러오기
    @Transactional
    public ResponseEntity<Integer> myBestScore(UUID userId) {
        String userIdStr = userId.toString();

        Double score = zsetOps.score("GameRank", userIdStr);
        if (score == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        } else {
            return ResponseEntity.ok(score.intValue());
        }
    }

    // top10 랭크 조회
    public ResponseEntity<List<GameRankDto>> topTenRank() {

        Set<ZSetOperations.TypedTuple<String>> rangeWithScores =
                zsetOps.reverseRangeWithScores("GameRank", 0, 9);

        AtomicInteger rank = new AtomicInteger(1);

        List<GameRankDto> topTen = rangeWithScores.stream()
                .map(tuple -> new GameRankDto(
                        UUID.fromString(tuple.getValue()), tuple.getScore(), rank.getAndIncrement()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(topTen);

    }

    // 라운드 확인
    @Transactional
    public Integer checkRound() {

        // 오늘을 기준으로 라운드를 계산한다.
        Long today = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        LocalDateTime releaseDate = LocalDateTime.of(2023, 10, 6, 0, 0);
        Long releaseDateL = releaseDate.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        Long differenceL = today - releaseDateL;

        // 7 * 24 * 60 * 60 * 1000 = 604800000 7일을 밀리초로 바꾼 값
        double roundD = (double) differenceL/604800000;
        // 나눈 뒤 소수점에서 올림하고 int로 바꿔준다
        int round = (int) Math.ceil(roundD);
        return round;
    }

}
