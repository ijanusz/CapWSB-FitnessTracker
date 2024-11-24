package com.capgemini.wsb.fitnesstracker.statistics.internal;

import lombok.Data;

@Data
class StatisticsDto {
    Long id;
    Long userId;
    int totalTrainings;
    double totalDistance;
    int totalCaloriesBurned;
}