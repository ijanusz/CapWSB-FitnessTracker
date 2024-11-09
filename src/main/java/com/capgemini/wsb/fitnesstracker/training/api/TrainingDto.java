package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;

import java.util.Date;

public record TrainingDto(@Nullable Long id, @Nullable User user, Date startTime,
                          Date endTime, ActivityType activityType, @Nullable double distance,
                          @Nullable double averageSpeed) {

}
