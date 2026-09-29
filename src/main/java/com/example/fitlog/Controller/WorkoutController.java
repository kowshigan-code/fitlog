package com.example.fitlog.controller;

import com.example.fitlog.entity.Workout;
import com.example.fitlog.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Workout createWorkout(
            @PathVariable Long userId,
            @Valid @RequestBody Workout workout) {

        return workoutService.createWorkout(userId, workout);
    }

    @GetMapping("/user/{userId}")
    public List<Workout> getWorkouts(
            @PathVariable Long userId) {

        return workoutService.getUserWorkouts(userId);
    }

    @GetMapping("/{id}")
    public Workout getWorkout(@PathVariable Long id) {

        return workoutService.getWorkout(id);
    }

    @GetMapping("/user/{userId}/weekly")
    public List<Workout> getWeeklyWorkouts(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate) {

        return workoutService.getWeeklyWorkouts(
                userId,
                startDate
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable Long id) {

        workoutService.deleteWorkout(id);
    }
}