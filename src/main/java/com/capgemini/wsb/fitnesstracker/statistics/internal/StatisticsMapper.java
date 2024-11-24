package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.model.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    public Statistics toEntity(StatisticsCreateRequestDto createRequestDto, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (createRequestDto.getDistance() < 0 || createRequestDto.getCalories() < 0) {
            throw new IllegalArgumentException("Statistics values cannot be negative");
        }

        return new Statistics(user, createRequestDto.getDistance(), createRequestDto.getCalories());
    }

    public StatisticsDto toDto(Statistics statistics) {
        StatisticsDto dto = new StatisticsDto();
        dto.setId(statistics.getId());
        dto.setUserId(statistics.getUser().getId());
        dto.setTotalTrainings(statistics.getTotalTrainings());
        dto.setTotalDistance(statistics.getTotalDistance());
        dto.setTotalCaloriesBurned(statistics.getTotalCaloriesBurned());
        return dto;
    }

    public Statistics updateEntity(Statistics existingStatistics, StatisticsUpdateRequestDto updateRequestDto) {
        if (updateRequestDto.getTotalTrainings() != null) {
            existingStatistics.setTotalTrainings(updateRequestDto.getTotalTrainings());
        }
        if (updateRequestDto.getTotalDistance() != null) {
            existingStatistics.setTotalDistance(updateRequestDto.getTotalDistance());
        }
        if (updateRequestDto.getTotalCaloriesBurned() != null) {
            existingStatistics.setTotalCaloriesBurned(updateRequestDto.getTotalCaloriesBurned());
        }
        return existingStatistics;
    }
}