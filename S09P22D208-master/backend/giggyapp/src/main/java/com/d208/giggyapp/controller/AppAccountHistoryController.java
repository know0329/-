package com.d208.giggyapp.controller;


import com.d208.giggyapp.dto.appAccountHistory.*;
import com.d208.giggyapp.repository.AppAccountHistoryRepository;
import com.d208.giggyapp.service.AppAccountHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app")
public class AppAccountHistoryController {
    private final AppAccountHistoryService appAccountHistoryService;

    // 은행거래내역 받아오고 분석
    @PostMapping("/account-history/bank")
    public ResponseEntity<?> getAppAccountHistory(@RequestBody UUID userId){
        // 은행으로부터 계좌거래내역 받아오기
        return appAccountHistoryService.getBankAccountHistory(userId);
    }

    // 거래내역 조회 할 수 있는 달
    @PostMapping("/account-history/month")
    public ResponseEntity<List<String>> getMonthAccountHistory(@RequestBody UUID userId){
        List<String> yearMonths = appAccountHistoryService.getMonth(userId);
        return ResponseEntity.ok(yearMonths);
    }

    // 특정 거래내역 카테고리 수정
    @PutMapping("/account-history/category")
    public ResponseEntity<?> updateAccountHistory(@RequestBody AccountHistoryDto accountHistoryDTO){
        Long id = accountHistoryDTO.getId();
        String category = accountHistoryDTO.getCategory();
        appAccountHistoryService.updateCategory(id, category);
        return ResponseEntity.ok(true);
    }

    // 거래내역 조회
    @PostMapping("/account-history/app")
    public ResponseEntity<List<AccountHistoryDto>> getAccountHistory(@RequestBody DateDto accountHistoryDateDto){
        List<AccountHistoryDto> accountHistoryDtos = appAccountHistoryService.getAppAccountHistory(accountHistoryDateDto);
        return ResponseEntity.ok(accountHistoryDtos);
    }

    // 분석내역 조회
    @PostMapping("/account-history/analysis")
    public  ResponseEntity<?> getAnalysis(@RequestBody MonthDto monthDTO){
        List<AnalysisDto> analysisDtos = appAccountHistoryService.getAnalysis(monthDTO);
        return ResponseEntity.ok(analysisDtos);
    }
}
