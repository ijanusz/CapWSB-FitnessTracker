package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.api.exception.UserNotFoundException;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new {@link User} entity in the system.
     * The created user will be persisted in the database.
     *
     * @param user The {@link User} object containing details of the user to be created
     * @return The created {@link User} entity with an assigned ID
     * @throws IllegalArgumentException if the user object is null or invalid
     */
    User createUser(User user);

    /**
     * Removes an existing {@link User} entity from the system.
     * The user is identified by their unique ID.
     *
     * @param userId The unique ID of the user to be removed
     * @throws IllegalArgumentException if the userId is null
     * @throws UserNotFoundException    if no user with the specified ID exists
     */
    void removeUser(Long userId);

    /**
     * Updates an existing {@link User} entity in the system.
     * The user details are updated based on the provided {@link User} object.
     *
     * @param entity The {@link User} object containing updated user details
     * @return The updated {@link User} entity
     * @throws IllegalArgumentException if the user object is null or invalid
     * @throws UserNotFoundException    if the user to update does not exist
     */
    User updateUser(User entity);
}
