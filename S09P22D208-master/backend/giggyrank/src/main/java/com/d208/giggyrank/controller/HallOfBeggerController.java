package com.d208.giggyrank.controller;

import com.d208.giggyrank.dto.BeggerRankDto;
import com.d208.giggyrank.dto.BeggerRankResultDto;
import com.d208.giggyrank.dto.MyBeggerRankDto;
import com.d208.giggyrank.service.HallOfBeggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rank")
public class HallOfBeggerController {
    private final HallOfBeggerService hallOfBeggerService;

    @PostMapping("/hall-of-begger/update")
    public ResponseEntity<?> updateBeggerRank(@RequestBody BeggerRankDto beggerRankDto) {
        long rank = hallOfBeggerService.updateScore(beggerRankDto);
        return ResponseEntity.ok(rank);
    }

    @GetMapping("/hall-of-begger")
    public ResponseEntity<?> getTopRank(){
        List<BeggerRankResultDto> beggerRankResults = hallOfBeggerService.getTopRanking();
        return ResponseEntity.ok(beggerRankResults);
    }

    @PostMapping("/hall-of-begger")
    public ResponseEntity<?> getUserRank(@RequestBody BeggerRankDto beggerRankDto){
        MyBeggerRankDto myBeggerRankDto = hallOfBeggerService.getUserRank(beggerRankDto);
        return ResponseEntity.ok(myBeggerRankDto);
    }
}
