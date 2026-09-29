package com.example.fitlog.repository;

import com.example.fitlog.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    Optional<Goal> findByUserIdAndWeekStart(
            Long userId,
            LocalDate weekStart
    );
}
