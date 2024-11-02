package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
public class OlderThanAgeStrategy implements UserFilterStrategy {
    private final Integer age;

    @Override
    public boolean filter(User user) {
        if (age == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate userBirthdate = user.getBirthdate();

        int userAge = Period.between(userBirthdate, today).getYears();

        return userAge > age;
    }

    @Override
    public boolean isApplicable(User user) {
        return user != null && age != null;
    }
}
