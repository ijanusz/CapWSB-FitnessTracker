package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.exception.StatisticsNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsServiceImpl statisticsService;
    private final StatisticsMapper statisticsMapper;
    private final UserProvider userProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticsDto createStatistics(@RequestBody @Valid StatisticsCreateRequestDto createRequestDto) {
        return userProvider.getUser(createRequestDto.getUserId())
                .map(user -> statisticsMapper.toEntity(createRequestDto, user))
                .map(statisticsService::createStatistics)
                .map(statisticsMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + createRequestDto.getUserId()));
    }

    @PutMapping("/{statisticsId}")
    public StatisticsDto updateStatistics(@PathVariable Long statisticsId, @RequestBody @Valid StatisticsUpdateRequestDto updateRequestDto) {
        return statisticsService.getStatistics(statisticsId)
                .map(existingStatistics -> statisticsMapper.updateEntity(existingStatistics, updateRequestDto))
                .map(statisticsService::updateStatistics)
                .map(statisticsMapper::toDto)
                .orElseThrow(() -> new StatisticsNotFoundException(statisticsId));
    }

    @GetMapping("/{userId}")
    public StatisticsDto getStatisticsForUser(@PathVariable Long userId) {
        return statisticsService.getStatisticsByUserId(userId)
                .map(statisticsMapper::toDto)
                .orElseThrow(() -> new StatisticsNotFoundException(userId));
    }

    @DeleteMapping("/{statisticsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatistics(@PathVariable Long statisticsId) {
        statisticsService.getStatistics(statisticsId)
                .ifPresentOrElse(
                        statistics -> statisticsService.deleteStatistics(statisticsId),
                        () -> {
                            throw new StatisticsNotFoundException(statisticsId);
                        }
                );
    }

    @GetMapping("/search/calories")
    public List<StatisticsDto> findStatisticsByCaloriesGreaterThan(@RequestParam("calories") int calories) {
        return statisticsService.findStatisticsByCaloriesGreaterThan(calories)
                .stream()
                .map(statisticsMapper::toDto)
                .toList();
    }
}