package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.model.Training;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }


    @Override
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public void removeTraining(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }


    @Override
    public Training updateTraining(Training training) {
        log.info("Updating Training {}", training);
        if (training.getId() == null) {
            throw new IllegalArgumentException("Cannot update training without ID");
        }
        return trainingRepository.save(training);
    }

    @Override
    public List<Training> findFinishedTrainingsAfterTime(Date afterTime) {
        return trainingRepository.findByEndTimeAfter(afterTime);
    }

    public Optional<List<Training>> getUserTraining(Long userId) {
        List<Training> trainings = trainingRepository.findByUserId(userId);
        return trainings.isEmpty() ? Optional.empty() : Optional.of(trainings);
    }

    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }


    @Override
    public List<Training> findTrainingsByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {
        return trainingRepository.findByUserIdAndStartTimeBetween(userId, startDate, endDate);
    }
}
