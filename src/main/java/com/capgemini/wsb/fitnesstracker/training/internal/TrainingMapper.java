package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingRequestDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public Training toEntity(CreateTrainingRequestDto createTrainingRequestDto, User user) {
        return new Training(
                user,
                createTrainingRequestDto.getStartTime(),
                createTrainingRequestDto.getEndTime(),
                ActivityType.valueOf(createTrainingRequestDto.getActivityType().toUpperCase()),
                createTrainingRequestDto.getDistance(),
                createTrainingRequestDto.getAverageSpeed()
        );
    }

    public TrainingDto toDto(Training training) {
        return TrainingDto.from(training);
    }

    public Training updateEntity(Training existingTraining, CreateTrainingRequestDto updateRequestDto, User user) {
        existingTraining.setUser(user);
        existingTraining.setStartTime(updateRequestDto.getStartTime());
        existingTraining.setEndTime(updateRequestDto.getEndTime());
        existingTraining.setActivityType(ActivityType.valueOf(updateRequestDto.getActivityType().toUpperCase()));
        existingTraining.setDistance(updateRequestDto.getDistance());
        existingTraining.setAverageSpeed(updateRequestDto.getAverageSpeed());
        return existingTraining;
    }

}
