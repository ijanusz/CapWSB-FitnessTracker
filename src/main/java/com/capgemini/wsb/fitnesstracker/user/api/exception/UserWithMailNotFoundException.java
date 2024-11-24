package com.capgemini.wsb.fitnesstracker.user.api.exception;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.model.User;

/**
 * Exception indicating that the {@link User} with given email address was not found.
 */
@SuppressWarnings("squid:S110")
public class UserWithMailNotFoundException extends NotFoundException {

    public UserWithMailNotFoundException(String id) {
        super("User with mail=%s was not found".formatted(id));
    }


}
