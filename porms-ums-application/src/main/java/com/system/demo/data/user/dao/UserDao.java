package com.system.demo.data.user.dao;


import com.system.demo.appl.model.user.User;

import java.util.List;

public interface UserDao {

    /**
     * Saves the given user to the database.
     *
     * @param user the user to be saved
     * @return the saved user
     */
    User saveUser(User user);

    /**
     * Retrieves the maximum user ID.
     *
     * @return the maximum user ID
     */
    long getMaxUserId();

    /**
     * Retrieves all users.
     *
     * @return list of all User
     */
    List<User> getAllUsers();

    boolean addUser(User user);
    /**
     * Updates a user.
     *
     * @return true if the update was successful, false otherwise
     */
    boolean updateUser(User user);


    /**
     * Updates the password for a user.
     *
     * @param user the user with the updated password
     * @return the updated User
     */
    User updatePassword(User user);

    /**
     * Retrieves the password for a user by username.
     *
     * @param username the username of the user
     * @return the password with the given username, or null if not found
     */
    String getPasswordByUsername(String username);

    /**
     * Resets the password for a user.
     *
     * @param username    the username of the user
     * @param newPassword the new password
     * @return the updated User
     */
    String forgotPassword(String username, String newPassword);

    User getUserByUserId(String userId);

    User getUserByUsername(String userName);

}


