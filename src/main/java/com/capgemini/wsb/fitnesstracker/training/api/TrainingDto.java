package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.Date;

@Builder
public record TrainingDto(@Nullable Long id,
                          @Nullable UserDto user,
                          Date startTime,
                          Date endTime,
                          ActivityType activityType,
                          @Nullable double distance,
                          @Nullable double averageSpeed) {

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
