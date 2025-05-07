package com.grittonbelldev.service;

import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for application-level operations involving User entities.
 *
 * <p>This service handles logic for locating users by Cognito ID,
 * creating new users if they do not exist, and updating profile details.
 * It abstracts persistence logic using a generic DAO layer.</p>
 */
public class UserService {

    // DAO instance for User entities
    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * Finds a User by Cognito subject identifier or creates one if not found.
     *
     * <p>If a matching User is not located using the "cognitoId" field,
     * a new User is instantiated with placeholder names and the provided email.</p>
     *
     * @param cognitoSub the Cognito subject (unique identifier)
     * @param email the user's email address
     * @return the existing or newly created User
     */
    public User findOrCreateByCognitoId(String cognitoSub, String email) {
        List<User> found = userDao.getByPropertyEqual("cognitoId", cognitoSub);
        if (!found.isEmpty()) {
            return found.get(0);
        }

        // This is a new user, so create one with required default values
        User u = new User();
        u.setCognitoId(cognitoSub);
        u.setEmail(email);
        u.setFirstName("New");   // Default placeholder
        u.setLastName("User");   // Default placeholder
        userDao.insert(u);
        return u;
    }

    /**
     * Retrieves a User entity by its internal numeric ID.
     *
     * @param id the primary key of the user in the database
     * @return the User entity or null if not found
     */
    public User getById(long id) {
        return userDao.getById(id);
    }

    /**
     * Updates selected profile fields for a given User.
     *
     * <p>Each parameter is optional; null values will result in no change
     * for the corresponding field.</p>
     *
     * @param id the internal user ID
     * @param firstName the new first name, or null to skip updating
     * @param lastName the new last name, or null to skip updating
     * @param email the new email address, or null to skip updating
     * @return the updated User object
     * @throws IllegalArgumentException if the user does not exist
     */
    public User updateProfile(long id, String firstName, String lastName, String email) {
        User u = userDao.getById(id);
        if (u == null) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        if (firstName != null) u.setFirstName(firstName);
        if (lastName  != null) u.setLastName(lastName);
        if (email     != null) u.setEmail(email);
        userDao.update(u);
        return u;
    }
}
