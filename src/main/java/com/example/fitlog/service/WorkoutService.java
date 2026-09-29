package com.example.fitlog.service;

import com.example.fitlog.entity.User;
import com.example.fitlog.entity.Workout;
import com.example.fitlog.exception.BadRequestException;
import com.example.fitlog.exception.ResourceNotFoundException;
import com.example.fitlog.repository.UserRepository;
import com.example.fitlog.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            UserRepository userRepository) {

        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public Workout createWorkout(Long userId, Workout workout) {

        if (workout.getCaloriesBurned() == null ||
                workout.getCaloriesBurned() < 0) {

            throw new BadRequestException(
                    "Calories burned cannot be negative"
            );
        }

        if (workout.getDuration() == null ||
                workout.getDuration() <= 0) {

            throw new BadRequestException(
                    "Duration must be greater than zero"
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        workout.setUser(user);

        return workoutRepository.save(workout);
    }

    public List<Workout> getUserWorkouts(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return workoutRepository.findByUserId(userId);
    }

    public Workout getWorkout(Long id) {

        return workoutRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workout not found"));
    }

    public void deleteWorkout(Long id) {

        Workout workout = getWorkout(id);

        workoutRepository.delete(workout);
    }

    public List<Workout> getWeeklyWorkouts(
            Long userId,
            LocalDate startDate) {

        LocalDate endDate = startDate.plusDays(6);

        return workoutRepository
                .findByUserIdAndWorkoutDateBetween(
                        userId,
                        startDate,
                        endDate
                );
    }
}