package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    /**
     * Finds all statistics with total calories burned greater than the specified value.
     *
     * @param calories the calorie threshold
     * @return a list of statistics
     */
    List<Statistics> findByTotalCaloriesBurnedGreaterThan(int calories);

    /**
     * Finds statistics for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the user's statistics, or empty if not found
     */
    Optional<Statistics> findByUserId(Long userId);
}
