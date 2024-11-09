package com.capgemini.wsb.fitnesstracker.training.api;

public interface TrainingService {
    Training createTraining(Training training);
    void removeTraining(Long trainingId);
    Training updateTraining(Training training);
}
