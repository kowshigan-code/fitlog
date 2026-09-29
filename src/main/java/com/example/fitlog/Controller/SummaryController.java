package com.example.fitlog.controller;

import com.example.fitlog.service.SummaryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/user/{userId}/weekly")
    public Map<String, Object> getWeeklySummary(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate) {

        return summaryService.getWeeklySummary(userId, startDate);
    }

    @GetMapping("/user/{userId}/daily")
    public Map<String, Object> getDailySummary(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {

        return summaryService.getDailySummary(userId, date);
    }
}