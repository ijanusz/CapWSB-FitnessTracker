package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.statistics.api.model.Statistics;

public interface StatisticsService {

    /**
     * Creates a new statistics entry.
     *
     * @param statistics the statistics to create
     * @return the created statistics
     */
    Statistics createStatistics(Statistics statistics);

    /**
     * Updates an existing statistics entry.
     *
     * @param statistics the statistics to update
     * @return the updated statistics
     */
    Statistics updateStatistics(Statistics statistics);

    /**
     * Deletes a statistics entry by its ID.
     *
     * @param statisticsId the ID of the statistics to delete
     */
    void deleteStatistics(Long statisticsId);


}
