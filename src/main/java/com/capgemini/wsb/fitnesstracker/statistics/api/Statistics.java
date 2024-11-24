package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "total_trainings", nullable = false)
    private int totalTrainings = 0;

    @Column(name = "total_distance", nullable = false)
    private double totalDistance = 0.0;

    @Column(name = "total_calories_burned", nullable = false)
    private int totalCaloriesBurned = 0;


    public Statistics(User user, double distance, int calories) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (distance < 0 || calories < 0) {
            throw new IllegalArgumentException("Statistics values cannot be negative");
        }
        this.user = user;
        addDistance(distance);
        addCaloriesBurned(calories);
        incrementTrainings();
    }

    public Statistics(User user, int trainings, double distance, int calories) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (trainings < 0 || distance < 0 || calories < 0) {
            throw new IllegalArgumentException("Statistics values cannot be negative");
        }
        this.user = user;
        this.totalTrainings = trainings;
        this.totalDistance = distance;
        this.totalCaloriesBurned = calories;
    }

    public void addDistance(double distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance to add cannot be negative");
        }
        this.totalDistance += distance;
    }

    public void addCaloriesBurned(int calories) {
        if (calories < 0) {
            throw new IllegalArgumentException("Calories to add cannot be negative");
        }
        this.totalCaloriesBurned += calories;
    }

    public void incrementTrainings() {
        this.totalTrainings++;
    }

    public void decrementTrainings() {
        if (this.totalTrainings == 0) {
            throw new IllegalStateException("Cannot decrement trainings below zero");
        }
        this.totalTrainings--;
    }
}