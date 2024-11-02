package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CompositeUserFilterStrategy implements UserFilterStrategy {
    private final List<UserFilterStrategy> strategies;

    @Override
    public boolean filter(User user) {
        return strategies.stream().anyMatch(strategy -> strategy.filter(user));
    }

    @Override
    public boolean isApplicable(User user) {
        return !strategies.isEmpty();
    }
}
