package com.capgemini.wsb.fitnesstracker.statistics.internal;

import lombok.Value;

@Value
public class StatisticsCreateRequestDto {
    Long userId;
    Integer calories;
    double distance;
}