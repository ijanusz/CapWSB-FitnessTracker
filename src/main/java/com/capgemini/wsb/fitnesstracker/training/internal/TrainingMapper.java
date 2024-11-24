package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingRequestDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import org.springframework.stereotype.Component;

@Component
class TrainingMapper {

    Training toEntity(CreateTrainingRequestDto createTrainingRequestDto, User user) {
        return new Training(
                user,
                createTrainingRequestDto.getStartTime(),
                createTrainingRequestDto.getEndTime(),
                ActivityType.valueOf(createTrainingRequestDto.getActivityType().toUpperCase()),
                createTrainingRequestDto.getDistance(),
                createTrainingRequestDto.getAverageSpeed()
        );
    }

    TrainingDto toDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .user(toDto(training.getUser()))
                .startTime(training.getStartTime())
                .endTime(training.getEndTime())
                .activityType(training.getActivityType())
                .distance(training.getDistance())
                .averageSpeed(training.getAverageSpeed())
                .build();
    }

    Training updateEntity(Training existingTraining, CreateTrainingRequestDto updateRequestDto, User user) {
        existingTraining.setUser(user);
        existingTraining.setStartTime(updateRequestDto.getStartTime());
        existingTraining.setEndTime(updateRequestDto.getEndTime());
        existingTraining.setActivityType(ActivityType.valueOf(updateRequestDto.getActivityType().toUpperCase()));
        existingTraining.setDistance(updateRequestDto.getDistance());
        existingTraining.setAverageSpeed(updateRequestDto.getAverageSpeed());
        return existingTraining;
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }
}