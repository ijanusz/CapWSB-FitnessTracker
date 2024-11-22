package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for managing Training entities.
 */
interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Query to find trainings by user ID.
     *
     * @param userId ID of the user whose trainings are to be retrieved
     * @return A {@link List} of trainings associated with the specified user
     */
    List<Training> findByUserId(Long userId);

    /**
     * Query to find trainings that ended after a specified time.
     *
     * @param afterTime The time after which trainings are to be retrieved
     * @return A {@link List} of trainings that ended after the specified time
     */
    List<Training> findByEndTimeAfter(Date afterTime);

    /**
     * Query to find trainings by activity type.
     *
     * @param activityType The type of activity for which trainings are to be retrieved
     * @return A {@link List} of trainings matching the specified activity type
     */
    List<Training> findByActivityType(ActivityType activityType);

    /**
     * Finds trainings by user ID within a specified date range.
     *
     * @param userId    ID of the user
     * @param startTime Start date of the range
     * @param endTime   End date of the range
     * @return List of trainings
     */
    List<Training> findByUserIdAndStartTimeBetween(Long userId, Date startTime, Date endTime);
}
