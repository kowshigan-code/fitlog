package com.example.fitlog.service;

import com.example.fitlog.entity.Meal;
import com.example.fitlog.entity.User;
import com.example.fitlog.exception.BadRequestException;
import com.example.fitlog.exception.ResourceNotFoundException;
import com.example.fitlog.repository.MealRepository;
import com.example.fitlog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public MealService(
            MealRepository mealRepository,
            UserRepository userRepository) {

        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public Meal createMeal(Long userId, Meal meal) {

        if (meal.getCalories() == null ||
                meal.getCalories() < 0) {

            throw new BadRequestException(
                    "Calories consumed cannot be negative"
            );
        }

        if (meal.getQuantity() == null ||
                meal.getQuantity() <= 0) {

            throw new BadRequestException(
                    "Quantity must be greater than zero"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        meal.setUser(user);

        return mealRepository.save(meal);
    }

    public List<Meal> getUserMeals(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return mealRepository.findByUserId(userId);
    }

    public Meal getMeal(Long id) {

        return mealRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meal not found"));
    }

    public void deleteMeal(Long id) {

        Meal meal = getMeal(id);

        mealRepository.delete(meal);
    }

    public List<Meal> getWeeklyMeals(
            Long userId,
            LocalDate startDate) {

        LocalDate endDate = startDate.plusDays(6);

        return mealRepository.findByUserIdAndMealDateBetween(
                userId,
                startDate,
                endDate
        );
    }
}