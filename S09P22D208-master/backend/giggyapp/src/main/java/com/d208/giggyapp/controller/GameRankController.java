package com.d208.giggyapp.controller;

import com.d208.giggyapp.dto.game.GameRankDto;
import com.d208.giggyapp.dto.game.MyStatusDto;
import com.d208.giggyapp.service.GameRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class GameRankController {

    private final GameRankService gameRankService;

    private final String RANK_URL = "https://j9d208.p.ssafy.io:8282/api/v1/rank";


    // 게임 로그 저장
    @PostMapping("/game/log")
    public ResponseEntity<String> saveGameLog(@RequestBody GameRankDto gameRankDto) {
        // 모바일에서 유저 아이디, 게임 점수를 받는다
        // app DB에도 저장하고 랭킹용 redis 서버에도 저장해야한다.
        // app DB에 저장하는 건 Service에 있고 Rank 서버에 저장하는 api가 있으니 요청만 하면 된다.
        System.out.println("gameRankDto : " + gameRankDto);
        gameRankService.saveLog(gameRankDto);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", gameRankDto.getUserId());
        map.put("score", gameRankDto.getScore());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(RANK_URL + "/game/new-score", request, String.class);

        if (response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 유저정보 입니다.");
        }

        return response;
    }

    // 내 랭킹, 최고 점수 정보
    @GetMapping("/game/my-rank/{userId}")
    public ResponseEntity<GameRankDto> checkMyRank(@PathVariable UUID userId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GameRankDto> response =
                restTemplate.getForEntity(RANK_URL + "/game/my-rank/" + userId, GameRankDto.class);

        return response;
    }

    // 내 랭킹, 최고 점수, 남은 목숨 정보
    @GetMapping("/game/my-status/{userId}")
    public ResponseEntity<MyStatusDto> checkMyStatus(@PathVariable UUID userId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MyStatusDto> response =
                restTemplate.getForEntity(RANK_URL + "/game/my-rank/" + userId, MyStatusDto.class);

        return gameRankService.myStatus(response.getBody());
    }

    // 현재 상위 10위 까지 조회
    @GetMapping("/game/top-ten")
    public List<GameRankDto> topTenRank() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<GameRankDto>> response =
                restTemplate.exchange(RANK_URL + "/game/top-ten/", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<GameRankDto>>() {
                        });

        List<GameRankDto> topTen = response.getBody();
        return gameRankService.topTenRank(topTen);
    }

    // 명예의 전당 등록
    @PostMapping("/game/hall-of-fame")
    public ResponseEntity<String> toHallOfFame(@RequestBody GameRankDto gameRankDto) {
        // Rank에서 App으로 Dto에 데이터를 담아서 보내준다.
        // 받는 정보는 UUID userId, int score, int round, 세 가지.
        // 이렇게 받은 정보를 App DB에 저장한다. 로직은 Service의 saveLog
        return gameRankService.toHallOfFame(gameRankDto);
    }
}
