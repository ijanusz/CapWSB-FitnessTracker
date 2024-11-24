package com.capgemini.wsb.fitnesstracker.statistics.internal;

import lombok.Data;

@Data
public class StatisticsUpdateRequestDto {
    private Integer totalTrainings;
    private Double totalDistance;
    private Integer totalCaloriesBurned;
}