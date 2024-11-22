package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.Date;


public record CreateTrainingRequestDto(
        Long userId,
        Date startTime,
        Date endTime,
        String activityType,
        Double distance,
        Double averageSpeed
) {
}