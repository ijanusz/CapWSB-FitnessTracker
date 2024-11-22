package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Value;

import java.util.Date;


/**
 * DTO representing training data.
 * Provides detailed information about a training session, including user, time, activity type, distance, and average speed.
 */
@Builder
@Value
public class TrainingDto {
    @Nullable
    Long id;
    @Nullable
    UserDto user;
    Date startTime;
    Date endTime;
    ActivityType activityType;
    @Nullable
    Double distance;
    @Nullable
    Double averageSpeed;

    /**
     * Constructs a new TrainingDto.
     *
     * @param id           the unique identifier of the training
     * @param user         the user associated with the training
     * @param startTime    the start time of the training session
     * @param endTime      the end time of the training session
     * @param activityType the type of activity performed during the training
     * @param distance     the distance covered during the training session
     * @param averageSpeed the average speed during the training session
     */
    public TrainingDto(@Nullable Long id,
                       @Nullable UserDto user,
                       Date startTime,
                       Date endTime,
                       ActivityType activityType,
                       @Nullable Double distance,
                       @Nullable Double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    /**
     * Converts a Training entity into a TrainingDto.
     *
     * @param training the Training entity to convert
     * @return a TrainingDto representing the given Training entity
     */
    public static TrainingDto from(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .user(UserDto.from(training.getUser()))
                .startTime(training.getStartTime())
                .endTime(training.getEndTime())
                .activityType(training.getActivityType())
                .distance(training.getDistance())
                .averageSpeed(training.getAverageSpeed())
                .build();
    }
}