/**
 * The UserFacade interface provides methods for managing users in the system.
 * It defines operations such as adding, updating, and retrieving user information.
 */
package com.system.demo.appl.facade.user;

import com.system.demo.appl.model.user.User;

import java.util.List;

public interface UserFacade {

    /**
     * Saves a new user in the database.
     *
     * @param user The user to be saved.
     * @return True if the user was successfully saved, false otherwise.
     */
    User saveUser(User user);

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users stored in the database.
     */
    List<User> getAllUsers();

    /**
     * Adds a new user to the system.
     *
     * @param user The user to be added.
     * @return True if the user was successfully added, false otherwise.
     */
    boolean addUser(User user);

    /**
     * Updates an existing user's information in the database.
     *
     * @param user The user with updated information.
     * @return True if the user's information was successfully updated, false otherwise.
     */
    boolean updateUser(User user);

    /**
     * Updates the password for a user in the database.
     *
     * @param user The user whose password needs to be updated.
     * @return The updated user object if the password update was successful, null otherwise.
     */
    User updatePassword(User user);

    /**
     * Handles the process of resetting a user's password.
     *
     * @param username                The username of the user.
     * @param securityQuestionAnswer  The answer to the security question.
     * @param newPassword             The new password to set for the user.
     * @return True if the password reset was successful, false otherwise.
     */
    boolean forgotPassword(String username, String securityQuestionAnswer, String newPassword);

    /**
     * Retrieves a user based on their user ID.
     *
     * @param userId The unique identifier of the user.
     * @return The user object corresponding to the provided user ID, or null if not found.
     */
    User getUserByUserId(String userId);

    /**
     * Retrieves a user based on their username.
     *
     * @param username The username of the user.
     * @return The user object corresponding to the provided username, or null if not found.
     */
    User getUserByUsername(String username);
}
