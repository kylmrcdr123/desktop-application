/**
 * The com.system.demo.appl.model.user package contains classes representing user-related models.
 */
package com.system.demo.appl.model.user;

import java.sql.Timestamp;

/**
 * User model class representing user information.
 */
public class User {
    private int isActive;
    private int isLocked;
    private long id;
    private Timestamp joinDate;
    private Timestamp lastLoginDate;
    private String authorities;
    private String otp;
    private String password;
    private String role;
    private String userId;
    private String username;

    /**
     * Constructs a new User instance with the provided properties.
     *
     * @param isActive       Whether the user is active.
     * @param isLocked       Whether the user is locked.
     * @param id             The ID of the user.
     * @param joinDate       The date when the user joined.
     * @param authorities    The authorities of the user.
     * @param otp            The OTP (One-Time Password) of the user.
     * @param password       The password of the user.
     * @param role           The role of the user.
     * @param userId         The identity ID of the user.
     * @param username       The username of the user.
     * @param lastLoginDate  The last login date of the user.
     */
    public User(int isActive, int isLocked, long id, Timestamp joinDate, String authorities, String otp, String password, String role, String userId, String username, Timestamp lastLoginDate) {
        this.isActive = isActive;
        this.isLocked = isLocked;
        this.id = id;
        this.joinDate = joinDate;
        this.authorities = authorities;
        this.otp = otp;
        this.password = password;
        this.role = role;
        this.userId = userId;
        this.username = username;
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Get whether the user is active.
     *
     * @return 1 if the user is active, 0 otherwise.
     */
    public int getIsActive() {
        return isActive;
    }

    /**
     * Set whether the user is active.
     *
     * @param isActive 1 if the user is active, 0 otherwise.
     */
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    /**
     * Get whether the user is locked.
     *
     * @return 1 if the user is locked, 0 otherwise.
     */
    public int getIsLocked() {
        return isLocked;
    }

    /**
     * Set whether the user is locked.
     *
     * @param isLocked 1 if the user is locked, 0 otherwise.
     */
    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * Get the ID of the user.
     *
     * @return The ID of the user.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the ID of the user.
     *
     * @param id The ID of the user.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the date when the user joined.
     *
     * @return The join date of the user.
     */
    public Timestamp getJoinDate() {
        return joinDate;
    }

    /**
     * Set the date when the user joined.
     *
     * @param joinDate The join date of the user.
     */
    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * Get the last login date of the user.
     *
     * @return The last login date of the user.
     */
    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * Set the last login date of the user.
     *
     * @param lastLoginDate The last login date of the user.
     */
    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * Get the authorities of the user.
     *
     * @return The authorities of the user.
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * Set the authorities of the user.
     *
     * @param authorities The authorities of the user.
     */
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    /**
     * Get the OTP (One-Time Password) of the user.
     *
     * @return The OTP of the user.
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Set the OTP (One-Time Password) of the user.
     *
     * @param otp The OTP of the user.
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the role of the user.
     *
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the role of the user.
     *
     * @param role The role of the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get the identity ID of the user.
     *
     * @return The identity ID of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the identity ID of the user.
     *
     * @param userId The identity ID of the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // Setter method for date_modified
    public void setDateModified(Timestamp dateModified) {
        // Set the date_modified field
    }

    // Setter method for entity_id
    public void setEntityId(String entityId) {
        // Set the entity_id field
    }

    // Setter method for date_created
    public void setDateCreated(Timestamp dateCreated) {
        // Set the date_created field
    }

}
