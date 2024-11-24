package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;

public interface UserFilterStrategy {
    boolean filter(User user);
    boolean isApplicable(User user);
}
