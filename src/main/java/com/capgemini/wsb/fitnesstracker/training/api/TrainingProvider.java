package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on its ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return A {@link List} containing all trainings
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves all finished trainings that occurred after the specified time.
     *
     * @param afterTime The time after which finished trainings should be retrieved
     * @return A {@link List} containing all finished trainings after the specified time
     */
    List<Training> findFinishedTrainingsAfterTime(Date afterTime);

    /**
     * Retrieves all trainings of the specified activity type.
     *
     * @param activityType The type of activity for which trainings should be retrieved
     * @return A {@link List} containing all trainings of the specified activity type
     */
    List<Training> findTrainingsByActivityType(ActivityType activityType);
}