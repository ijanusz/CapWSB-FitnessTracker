package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;

public interface UserFilterStrategy {
    public boolean filter(User user);
    public boolean isApplicable(User user);
}
