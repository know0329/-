package com.d208.giggyapp.controller;

import com.d208.giggyapp.dto.begger.BeggerRankWeekDto;
import com.d208.giggyapp.service.HallOfBeggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class HallOfBeggerController {
    private final HallOfBeggerService hallOfBeggerService;

    @GetMapping("/hall-of-begger/top")
    public ResponseEntity<?> getTopHallOfBegger() {
        return hallOfBeggerService.getTopHallOfBegger();
    }

    @PostMapping("/hall-of-begger")
    public ResponseEntity<?> getHallOfBegger(@RequestBody UUID userId){
        return hallOfBeggerService.getHallOfBegger(userId);
    }

    @PostMapping("/hall-of-begger/week")
    public ResponseEntity<?> updateWeekHallofBegger(@RequestBody BeggerRankWeekDto beggerRankWeekDto){
        return hallOfBeggerService.updateWeek(beggerRankWeekDto);
    }
}
