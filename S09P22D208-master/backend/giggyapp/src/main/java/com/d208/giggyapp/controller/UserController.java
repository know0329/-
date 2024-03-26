package com.d208.giggyapp.controller;

import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.dto.SignUpDto;
import com.d208.giggyapp.dto.user.*;
import com.d208.giggyapp.repository.UserRepository;
import com.d208.giggyapp.service.RedisService;
import com.d208.giggyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class UserController {
    private final UserService userService;
    private final RedisService redisService;

    private final UserRepository userRepository;

    // 카카오 토큰으로 회원의 이메일을 들고온다.
    // 이메일을 통해 회원 정보를 DB에서 조회한다
    // 회원 O -> 회원 정보 반환
    // 회원 X -> 이메일만 담인 회원 반환
    @PostMapping("/user/login")
    public ResponseEntity<SendUserDto> login(@RequestBody LoginDto loginDto) {
        // 카카오 정보 확인
        KakaoResponseDto kakaoResponseDto = userService.getKaKaoInfo(loginDto.getAccessToken());

        // 이메일로 회원 조회
        SendUserDto userDto = userService.userExist(kakaoResponseDto, loginDto);

        // 빈 유저가 아닌 경우에만 액세스 토큰을 레디스에 보관
        if (userDto.getRefreshToken() != null) {
            redisService.setAccessToken(TokenDto.builder().
                    accessToken(loginDto.getAccessToken()).
                    exist(1).
                    build());
        }

        return ResponseEntity.ok(userDto);
    }

    // 넘어온 회원 Dto를 가지고 회원 정보를 저장한다.
    @PostMapping("/user/signup")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }

    // 회원 닉네임 중복 체크
    @PostMapping("/user/nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody UserDto userDto) {
        return userService.checkNickname(userDto);
    }

    @PostMapping("/user/uuid")
    public User getUUID(@RequestBody UserDto userDto) {
        return userRepository.findById(userDto.getId()).orElse(null);
    }

    // 토큰이 만료됨을 보낸다.
    @PostMapping("/user/expired")
    public ResponseEntity<String> expired() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다");
    }

    // 토큰 재발급
    @PostMapping("/user/refresh")
    public ResponseEntity<String> refresh(@RequestHeader HttpHeaders header) {
        String refreshToken = header.getFirst("Authorization");
        redisService.issueAccessToken(refreshToken);

        return ResponseEntity.ok("gg");
    }

    // 목표 소비액 설정
    @PutMapping("/user/targetamount")
    public ResponseEntity<Boolean> setTargetAmount(@RequestBody UserDto userDto) {
        return userService.setTargetAmount(userDto);
    }

    // 게임 횟수 증가
    @PutMapping("/user/life/increase")
    public ResponseEntity<String> increaseLife(@RequestBody UserDto userDto) {
        return userService.incraseLife(userDto);
    }
    // 게임 횟수 감소
    @PutMapping("/user/life/decrease")
    public ResponseEntity<String> decreaseLife(@RequestBody UserDto userDto) {
        return userService.decreaseLife(userDto);
    }

    // 게임 횟수 초기화
    @PutMapping("/user/life/init")
    public ResponseEntity<String> initLife(@RequestBody UserDto userDto) {
        return userService.initLife(userDto);
    }

    // 게임 횟수 조회
    @PostMapping("/user/life")
    public ResponseEntity<String> getLife(@RequestBody UserDto userDto) {
        return userService.getLife(userDto);
    }

    // UUID로 정보 조회
    @PostMapping("/user/info")
    public ResponseEntity<SendUserDto> getUser(@RequestBody UserDto userDto) { return userService.getUser(userDto);}

}
