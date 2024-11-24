package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

/**
 * DTO for creating a new training request.
 */
@Value
@Builder
public class CreateTrainingRequestDto {
    Long userId;
    Date startTime;
    Date endTime;
    String activityType;
    Double distance;
    Double averageSpeed;

    /**
     * Constructs a new CreateTrainingRequestDto.
     *
     * @param userId       the ID of the user creating the training
     * @param startTime    the start time of the training
     * @param endTime      the end time of the training
     * @param activityType the type of activity (e.g., "Running", "Cycling")
     * @param distance     the distance covered during the training
     * @param averageSpeed the average speed during the training
     */
    public CreateTrainingRequestDto(Long userId, Date startTime, Date endTime, String activityType, Double distance, Double averageSpeed) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}