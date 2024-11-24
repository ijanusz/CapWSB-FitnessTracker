package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import com.capgemini.wsb.fitnesstracker.statistics.api.model.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService, StatisticsProvider {

    private final StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        log.info("Retrieving statistics with ID: {}", statisticsId);
        return statisticsRepository.findById(statisticsId);
    }

    @Override
    public Optional<Statistics> getStatisticsByUserId(Long userId) {
        log.info("Retrieving statistics for user with ID: {}", userId);
        return statisticsRepository.findByUserId(userId);
    }

    @Override
    public Statistics createStatistics(Statistics statistics) {
        log.info("Creating new statistics: {}", statistics);
        return statisticsRepository.save(statistics);
    }

    @Override
    public Statistics updateStatistics(Statistics statistics) {
        if (statistics.getId() == null) {
            throw new IllegalArgumentException("Cannot update statistics without an ID.");
        }
        log.info("Updating statistics: {}", statistics);
        return statisticsRepository.save(statistics);
    }

    @Override
    public void deleteStatistics(Long statisticsId) {
        log.info("Deleting statistics with ID: {}", statisticsId);
        statisticsRepository.deleteById(statisticsId);
    }

    @Override
    public List<Statistics> findStatisticsByCaloriesGreaterThan(int calories) {
        log.info("Finding statistics with calories greater than: {}", calories);
        return statisticsRepository.findByTotalCaloriesBurnedGreaterThan(calories);
    }
}
