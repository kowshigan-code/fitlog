package com.example.fitlog.controller;

import com.example.fitlog.entity.Goal;
import com.example.fitlog.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Goal createGoal(
            @PathVariable Long userId,
            @Valid @RequestBody Goal goal) {

        return goalService.createGoal(userId, goal);
    }

    @GetMapping("/{id}")
    public Goal getGoal(@PathVariable Long id) {

        return goalService.getGoal(id);
    }

    @GetMapping("/user/{userId}/weekly")
    public Goal getWeeklyGoal(
            @PathVariable Long userId,
            @RequestParam LocalDate weekStart) {

        return goalService.getWeeklyGoal(
                userId,
                weekStart
        );
    }
}