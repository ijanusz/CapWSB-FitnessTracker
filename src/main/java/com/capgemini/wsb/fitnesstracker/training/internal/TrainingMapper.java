package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {


    TrainingDto toDto(Training training) {
        return TrainingDto.from(training);
    }

}
