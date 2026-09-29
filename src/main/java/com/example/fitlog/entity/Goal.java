package com.example.fitlog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Weekly workout target is required")
    @Min(value = 1, message = "Goal must be at least 1 workout")
    private Integer weeklyWorkoutCount;

    @NotNull(message = "Week start date is required")
    private LocalDate weekStart;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Goal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeeklyWorkoutCount() {
        return weeklyWorkoutCount;
    }

    public void setWeeklyWorkoutCount(Integer weeklyWorkoutCount) {
        this.weeklyWorkoutCount = weeklyWorkoutCount;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}