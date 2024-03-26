package com.d208.giggyapp.service;

import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.domain.game.GameHistory;
import com.d208.giggyapp.domain.game.HallOfFame;
import com.d208.giggyapp.dto.game.GameRankDto;
import com.d208.giggyapp.dto.game.MyStatusDto;
import com.d208.giggyapp.repository.GameHistoryRepository;
import com.d208.giggyapp.repository.HallOfFameRepository;
import com.d208.giggyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameRankService {

    private final HallOfFameRepository hallOfFameRepository;
    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository;

    // 게임 로그 저장
    @Transactional
    public ResponseEntity<String> saveLog(GameRankDto gameRankDto) {
        User user = userRepository.findById(gameRankDto.getUserId()).orElse(null);
        user.decreaseLife();

        GameHistory gameHistory = GameHistory.builder()
                .score(gameRankDto.getScore())
                .round(checkRound())
                .user(user)
                .build();

        gameHistoryRepository.save(gameHistory);
        return ResponseEntity.ok("기록 저장 완료");
    }


    // 게임 최고 점수(1위) 명예의 전당에 저장
    @Transactional
    public ResponseEntity<String> toHallOfFame(GameRankDto gameRankDto) {  // 받는 정보 : 유저 아이디, 점수

        User user = userRepository.findById(gameRankDto.getUserId()).orElse(null);

        HallOfFame hallOfFame = HallOfFame.builder()
                .score(gameRankDto.getScore())
                .userId(gameRankDto.getUserId())
                .nickname(user.getNickname())
                .round(checkRound())
                .build();

        hallOfFameRepository.save(hallOfFame);

        return ResponseEntity.ok("명예의 전당 등록 완료");
    }

    @Transactional
    public Integer checkRound() {

        // 오늘을 기준으로 라운드를 계산한다.
        Long today = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        LocalDateTime releaseDate = LocalDateTime.of(2023, 10, 2, 0, 0);
        Long releaseDateL = releaseDate.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        Long differenceL = today - releaseDateL;

        // 7 * 24 * 60 * 60 * 1000 = 604800000 7일을 밀리초로 바꾼 값
        double roundD = (double) differenceL/604800000;
        // 나눈 뒤 소수점에서 올림하고 int로 바꿔준다
        int round = (int) Math.ceil(roundD);
        return round;
    }

    public List<GameRankDto> topTenRank(List<GameRankDto>  topTen) {
        List<GameRankDto> result = new ArrayList<>();

        for (GameRankDto gameRankDto : topTen) {
            String nickname = userRepository.findById(gameRankDto.getUserId()).orElse(null).getNickname();

            GameRankDto rankWithNicks = new GameRankDto(
                    gameRankDto.getUserId(),
                    gameRankDto.getScore(),
                    gameRankDto.getRank() + 1,
                    nickname
                    );

            result.add(rankWithNicks);
        }

        return result;
    }

    public ResponseEntity<MyStatusDto> myStatus(MyStatusDto body) {
        User user = userRepository.findById(body.getUserId()).orElse(null);

        body.setLeftLife(user.getLeftLife());
        return ResponseEntity.ok(body);
    }
}
