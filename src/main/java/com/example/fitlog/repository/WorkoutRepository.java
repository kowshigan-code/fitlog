package com.example.fitlog.repository;

import com.example.fitlog.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUserId(Long userId);

    List<Workout> findByUserIdAndWorkoutDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    long countByUserIdAndWorkoutDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}
