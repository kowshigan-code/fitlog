package com.example.fitlog.repository;

import com.example.fitlog.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByUserId(Long userId);

    List<Meal> findByUserIdAndMealDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}
