package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Implements the user service create and read users
 */
@Service
public class UserService {

    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Checks if the username is available to be assigned to a new user
     * @param username the username to be checked
     * @return a boolean indicating if the username is available
     */
    public boolean isUsernameAvailable(String username) {
        return userMapper.findUser(username) == null;
    }

    /**
     * Creates a user with a hashed password in the database
     * @param user A user object
     * @return id of the new user
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.createUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    /**
     * Gets user information by username
     * @param username the username of the user to gather information on
     * @return the requested user
     */
    public User getUser(String username) {
        return userMapper.findUser(username);
    }
}
