package com.d208.giggyrank.service;

import com.d208.giggyrank.dto.BeggerRankDto;
import com.d208.giggyrank.dto.BeggerRankResultDto;
import com.d208.giggyrank.dto.MyBeggerRankDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class HallOfBeggerService {
    private final ZSetOperations<String, String> zSetOperations;

    public HallOfBeggerService(RedisTemplate<String, String> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }
    private final String rankingKey = "begger_ranking";

    public long updateScore(BeggerRankDto beggerRankDto){
        double ratio = (double) beggerRankDto.getCurrentAmount() /beggerRankDto.getTargetAmount();
        String userId = beggerRankDto.getUserId().toString();
        System.out.println(ratio);
        zSetOperations.add(rankingKey, userId, ratio);

        // Redis ZSet의 rank는 0부터 시작하며, 작은 점수가 0번째입니다.
        // 따라서 큰 점수일수록 rank 값이 크고, 그러므로 순위는 작아집니다.
        // 순위를 얻기 위해선 전체 사이즈에서 rank 값을 빼주고 1을 더해야 합니다.
        Long rank = zSetOperations.reverseRank(rankingKey, beggerRankDto.getUserId().toString());

        if (rank == null) {
            throw new RuntimeException("Failed to get the rank of user: " + beggerRankDto.getUserId());
        }

        return (zSetOperations.size(rankingKey) - rank);
    }


    public List<BeggerRankResultDto> getTopRanking(){
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = zSetOperations.rangeWithScores(rankingKey, 0, 9);

        return rangeWithScores.stream()
                .map(tuple -> new BeggerRankResultDto(UUID.fromString(tuple.getValue()), tuple.getScore()))
                .collect(Collectors.toList());
    }

    public MyBeggerRankDto getUserRank(BeggerRankDto beggerRankDto) {
        Long rank = zSetOperations.reverseRank(rankingKey, beggerRankDto.getUserId().toString());

        if (rank == null) {
            throw new RuntimeException("Failed to get the rank of user: " + beggerRankDto.getUserId());
        }

        Double score = zSetOperations.score(rankingKey, beggerRankDto.getUserId().toString());

        if (score == null) {
            throw new RuntimeException("Failed to get the score of user: " + beggerRankDto.getUserId());
        }

        MyBeggerRankDto myBeggerRankDto = MyBeggerRankDto.builder()
                .rank(zSetOperations.size(rankingKey) - rank)
                .ratio(score)
                .build();

        return myBeggerRankDto;
    }


}
