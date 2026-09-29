package com.example.fitlog.service;

import com.example.fitlog.entity.Meal;
import com.example.fitlog.entity.Workout;
import com.example.fitlog.exception.BadRequestException;
import com.example.fitlog.exception.ResourceNotFoundException;
import com.example.fitlog.repository.MealRepository;
import com.example.fitlog.repository.UserRepository;
import com.example.fitlog.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final MealRepository mealRepository;

    public SummaryService(UserRepository userRepository,
                          WorkoutRepository workoutRepository,
                          MealRepository mealRepository) {

        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.mealRepository = mealRepository;
    }

    // Daily Summary
    public Map<String, Object> getDailySummary(
            Long userId,
            LocalDate date) {

        // Check user
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        // Get workouts for the date
        List<Workout> workouts =
                workoutRepository.findByUserIdAndWorkoutDateBetween(
                        userId,
                        date,
                        date
                );

        // Get meals for the date
        List<Meal> meals =
                mealRepository.findByUserIdAndMealDateBetween(
                        userId,
                        date,
                        date
                );

        // Check data
        if (workouts.isEmpty() && meals.isEmpty()) {
            throw new BadRequestException(
                    "No workout or meal data found for this date"
            );
        }

        // Calculate calories burned
        int caloriesBurned = workouts.stream()
                .mapToInt(Workout::getCaloriesBurned)
                .sum();

        // Calculate calories consumed
        int caloriesConsumed = meals.stream()
                .mapToInt(Meal::getCalories)
                .sum();

        // Calculate net calories
        int netCalories =
                caloriesConsumed - caloriesBurned;

        // Create response
        Map<String, Object> summary = new HashMap<>();

        summary.put("date", date);
        summary.put("caloriesConsumed", caloriesConsumed);
        summary.put("caloriesBurned", caloriesBurned);
        summary.put("netCalories", netCalories);

        return summary;
    }


    // Weekly Summary
    public Map<String, Object> getWeeklySummary(
            Long userId,
            LocalDate startDate) {

        // Check user
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        // Calculate end date
        LocalDate endDate =
                startDate.plusDays(6);

        // Get workouts for the week
        List<Workout> workouts =
                workoutRepository.findByUserIdAndWorkoutDateBetween(
                        userId,
                        startDate,
                        endDate
                );

        // Get meals for the week
        List<Meal> meals =
                mealRepository.findByUserIdAndMealDateBetween(
                        userId,
                        startDate,
                        endDate
                );

        // Check data
        if (workouts.isEmpty() && meals.isEmpty()) {
            throw new BadRequestException(
                    "No workout or meal data found for this week"
            );
        }

        // Calculate calories burned
        int caloriesBurned = workouts.stream()
                .mapToInt(Workout::getCaloriesBurned)
                .sum();

        // Calculate calories consumed
        int caloriesConsumed = meals.stream()
                .mapToInt(Meal::getCalories)
                .sum();

        // Calculate net calories
        int netCalories =
                caloriesConsumed - caloriesBurned;

        // Create response
        Map<String, Object> summary = new HashMap<>();

        summary.put("weekStart", startDate);
        summary.put("weekEnd", endDate);
        summary.put("workoutsCompleted", workouts.size());
        summary.put("caloriesConsumed", caloriesConsumed);
        summary.put("caloriesBurned", caloriesBurned);
        summary.put("netCalories", netCalories);

        return summary;
    }
}