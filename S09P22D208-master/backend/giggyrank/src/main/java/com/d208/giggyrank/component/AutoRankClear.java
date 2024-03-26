package com.d208.giggyrank.component;

import com.d208.giggyrank.service.GameRankService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class AutoRankClear {

    private final RedisTemplate<String, String> redisTemplate;
    private final GameRankService gameRankService;
    private ZSetOperations<String, String> zsetOps;


    public AutoRankClear(RedisTemplate<String, String> redisTemplate, GameRankService gameRankService) {
        this.redisTemplate = redisTemplate;
        this.gameRankService = gameRankService;
        this.zsetOps = redisTemplate.opsForZSet();
    }


    // 자동 삭제 및 명예의 전당 등록 로직
    @Scheduled(cron = "0 0 0 ? * MON", zone = "Asia/Seoul")  // "0 * * * * ?"
    @Transactional
    public void autoClear() {
        // 일단 라운드 정보와 저장된 1등의 정보를 App으로 보낸다.
        int round = gameRankService.checkRound();

        RestTemplate restTemplate = new RestTemplate();

        Set<String> topPlayerSet = zsetOps.reverseRange("GameRank", 0, 0);
        if (topPlayerSet == null || topPlayerSet.isEmpty()) {
            throw new RuntimeException("No player data in GameRank");
        }

        Set<String> topBeggerSet = zsetOps.reverseRange("begger_rankiing",0,0);
        if(topBeggerSet == null || topBeggerSet.isEmpty()){
            throw new RuntimeException("No begger data in BeggerRank");
        }

        String topPlayerId = topPlayerSet.iterator().next();
        Double topScore = zsetOps.score("GameRank", topPlayerId);

        String topBeggerId = topBeggerSet.iterator().next();
        Double ratio = zsetOps.score("begger_ranking", topBeggerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        Map<String, Object> map = new HashMap<>();
        map.put("userId", UUID.fromString(topPlayerId));
        map.put("score", topScore.intValue());
        map.put("round", round);

        Map<String, Object> beggerMap = new HashMap<>();
        map.put("userId", UUID.fromString(topBeggerId));
        map.put("ratio", ratio);
        map.put("round", round);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

        HttpEntity<Map<String, Object>> beggerRequest = new HttpEntity<>(beggerMap, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity("https://j9d208.p.ssafy.io:8282/api/v1/app/game/hall-of-fame", request, String.class);

        ResponseEntity<Boolean> beggerResponse =
                restTemplate.postForEntity("https://j9d208.p.ssafy.io:8282/api/v1/app/hall-of-begger/week",beggerRequest, Boolean.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody().equals("명예의 전당 등록 완료")) {
            // 그리고 정상적으로 받았다면 삭제 진행
            redisTemplate.delete("GameRank");
        } else {
            System.out.println("삭제가 진행되지 않았습니다.");
        }

        if (response.getStatusCode().is2xxSuccessful() && beggerResponse.getBody()) {
            redisTemplate.delete("begger_ranking");
        }else {
            System.out.println("삭제가 진행되지 않았습니다.");
        }
    }
}
