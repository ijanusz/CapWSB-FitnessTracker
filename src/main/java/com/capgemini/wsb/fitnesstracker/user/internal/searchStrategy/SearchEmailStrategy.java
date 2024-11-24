package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchEmailStrategy implements UserFilterStrategy {
    private final String email;

    @Override
    public boolean filter(User user) {
        if (email == null) {
            return true;
        }

        return user.getEmail().toLowerCase().contains(email.toLowerCase());
    }

    @Override
    public boolean isApplicable(User user) {
        return user != null && email != null;
    }
}
