package com.grittonbelldev.service;

import com.grittonbelldev.entity.User;
import com.grittonbelldev.persistence.GenericDAO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for application‐level User operations.
 *
 * Handles finding or auto‐creating the internal User record given a Cognito sub,
 * as well as basic retrieval and profile updates.
 */
public class UserService {

    private final GenericDAO<User> userDao = new GenericDAO<>(User.class);

    /**
     * Look up by Cognito sub; if missing, create a new User with sub+email.
     */
    public User findOrCreateByCognitoId(String cognitoSub, String email) {
        List<User> found = userDao.getByPropertyEqual("cognitoId", cognitoSub);
        if (!found.isEmpty()) {
            return found.get(0);
        }
        // first-time user → must set non-null fields
        User u = new User();
        u.setCognitoId(cognitoSub);
        u.setEmail(email);
        u.setFirstName("New");   // placeholder
        u.setLastName("User");   // placeholder
        userDao.insert(u);
        return u;
    }

    /**
     * Retrieve a User by their internal numeric ID.
     *
     * @param id the primary key in your Users table
     * @return the User, or null if not found
     */
    public User getById(long id) {
        return userDao.getById(id);
    }

    /**
     * Update profile fields for a User.
     *
     * @param id the internal user ID
     * @param firstName new first name (or null to leave unchanged)
     * @param lastName  new last name (or null to leave unchanged)
     * @param email     new email (or null to leave unchanged)
     * @return the updated User
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