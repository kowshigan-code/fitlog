package com.example.fitlog.controller;

import com.example.fitlog.entity.Meal;
import com.example.fitlog.service.MealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Meal createMeal(
            @PathVariable Long userId,
            @Valid @RequestBody Meal meal) {

        return mealService.createMeal(userId, meal);
    }

    @GetMapping("/user/{userId}")
    public List<Meal> getMeals(
            @PathVariable Long userId) {

        return mealService.getUserMeals(userId);
    }

    @GetMapping("/{id}")
    public Meal getMeal(@PathVariable Long id) {

        return mealService.getMeal(id);
    }

    @GetMapping("/user/{userId}/weekly")
    public List<Meal> getWeeklyMeals(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate) {

        return mealService.getWeeklyMeals(
                userId,
                startDate
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable Long id) {

        mealService.deleteMeal(id);
    }
}
