package com.capgemini.wsb.fitnesstracker.statistics.api;

import java.util.List;
import java.util.Optional;

public interface StatisticsProvider {

    /**
     * Retrieves a statistics based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param statisticsId id of the statistics to be searched
     * @return An {@link Optional} containing the located Statistics, or {@link Optional#empty()} if not found
     */
    Optional<Statistics> getStatistics(Long statisticsId);

    /**
     * Finds statistics where total calories burned is greater than the specified value.
     *
     * @param calories the calorie threshold
     * @return a list of statistics matching the criteria
     */
    List<Statistics> findStatisticsByCaloriesGreaterThan(int calories);


    /**
     * Retrieves statistics for a specific user by user ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the user's statistics, or empty if not found
     */
    Optional<Statistics> getStatisticsByUserId(Long userId);
}
