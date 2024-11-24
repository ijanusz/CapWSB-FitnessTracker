package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.model.Training;

/**
 * Service interface for managing training operations.
 * Provides methods to create, update, and remove training sessions.
 */
public interface TrainingService {

    /**
     * Creates a new training session.
     *
     * @param training the training session to be created
     * @return the created training session
     */
    Training createTraining(Training training);

    /**
     * Removes an existing training session by its ID.
     *
     * @param trainingId the ID of the training session to be removed
     */
    void removeTraining(Long trainingId);

    /**
     * Updates an existing training session.
     *
     * @param training the training session with updated details
     * @return the updated training session
     */
    Training updateTraining(Training training);
}