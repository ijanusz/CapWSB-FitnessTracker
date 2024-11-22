package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingRequestDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public Training toEntity(CreateTrainingRequestDto dto, User user) {
        return new Training(
                user,
                dto.startTime(),
                dto.endTime(),
                ActivityType.valueOf(dto.activityType().toUpperCase()),
                dto.distance(),
                dto.averageSpeed()
        );
    }

    public TrainingDto toDto(Training training) {
        return TrainingDto.from(training);
    }

    public Training updateEntity(Training existingTraining, CreateTrainingRequestDto updateRequestDto, User user) {
        existingTraining.setUser(user);
        existingTraining.setStartTime(updateRequestDto.startTime());
        existingTraining.setEndTime(updateRequestDto.endTime());
        existingTraining.setActivityType(ActivityType.valueOf(updateRequestDto.activityType().toUpperCase()));
        existingTraining.setDistance(updateRequestDto.distance());
        existingTraining.setAverageSpeed(updateRequestDto.averageSpeed());
        return existingTraining;
    }

}
