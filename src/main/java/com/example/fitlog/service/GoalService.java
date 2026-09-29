package com.example.fitlog.service;

import com.example.fitlog.entity.Goal;
import com.example.fitlog.entity.User;
import com.example.fitlog.exception.BadRequestException;
import com.example.fitlog.exception.ResourceNotFoundException;
import com.example.fitlog.repository.GoalRepository;
import com.example.fitlog.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalService(
            GoalRepository goalRepository,
            UserRepository userRepository) {

        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    public Goal createGoal(Long userId, Goal goal) {

        if (goal.getWeeklyWorkoutCount() == null ||
                goal.getWeeklyWorkoutCount() <= 0) {

            throw new BadRequestException(
                    "Weekly workout goal must be greater than zero"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (goalRepository.findByUserIdAndWeekStart(
                userId,
                goal.getWeekStart()
        ).isPresent()) {

            throw new BadRequestException(
                    "Goal already exists for this week"
            );
        }

        goal.setUser(user);

        return goalRepository.save(goal);
    }

    public Goal getGoal(Long id) {

        return goalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Goal not found"));
    }

    public Goal getWeeklyGoal(
            Long userId,
            java.time.LocalDate weekStart) {

        return goalRepository
                .findByUserIdAndWeekStart(userId, weekStart)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weekly goal not found"
                        ));
    }
}