package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingRequestDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getUserTraining(@PathVariable("userId") Long trainingId) {
        return trainingService.getUserTraining(trainingId)
                .map(a -> a.stream()
                        .map(trainingMapper::toDto)
                        .toList())
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfterTime(@PathVariable("afterTime") String afterTime) {
        Date parsedDate;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(afterTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }

        return trainingService.findFinishedTrainingsAfterTime(parsedDate).stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam("activityType") String activityType) {
        ActivityType type;
        try {
            type = ActivityType.valueOf(activityType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid activity type: " + activityType);
        }

        return trainingService.findTrainingsByActivityType(type).stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto addTraining(@RequestBody @Valid CreateTrainingRequestDto createTrainingRequestDto) {
        User user = userProvider.getUser(createTrainingRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + createTrainingRequestDto.getUserId()));

        Training training = trainingMapper.toEntity(createTrainingRequestDto, user);
        Training createdTraining = trainingService.createTraining(training);
        return trainingMapper.toDto(createdTraining);
    }

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable("trainingId") Long trainingId, @RequestBody @Valid CreateTrainingRequestDto updateRequestDto) {
        Training existingTraining = trainingService.getTraining(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        User user = userProvider.getUser(updateRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + updateRequestDto.getUserId()));

        Training updatedTraining = trainingMapper.updateEntity(existingTraining, updateRequestDto, user);
        Training savedTraining = trainingService.updateTraining(updatedTraining);

        return trainingMapper.toDto(savedTraining);
    }


}
