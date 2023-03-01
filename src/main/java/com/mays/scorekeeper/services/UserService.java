package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.repositories.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A business logic service class for User objects.
 */
@Data
@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository autowired UserRepository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new User record if it does not exist.
     *
     * @param username the username
     * @param password the password
     * @return the created User object, or blank if unsuccessful
     */
    public Optional<User> create(String username, String password) {
         if (userRepository.existsByUsername(username)) {
             log.warn("Attempted to create a user record with username: " + username + " but name already exists");
             return Optional.empty();
         }
         log.info("Creating user with name: " + username);
        return Optional.of(userRepository.save(new User(username, password)));
    }

    /**
     * Retrieve a User record from the database.
     *
     * @param id the id to search for
     * @return a user if found
     */
    public Optional<User> get(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("Attempted to retrieve user record with ID: " + id + " but no record found");
        } else {
            log.info("Retrieved user record " + user.get().getUsername());
        }
        return user;
    }

    /**
     * Delete a User record
     *
     * @param id the id of the user to be deleted.
     */
    public void delete(Integer id) {
        log.info("Deleting User record with id: " + id);
        userRepository.deleteById(id);
    }

    /**
     * Update a User record
     *
     * @param user a new User record to replace the old one
     */
    public void update(User user) {
        log.info("Updating user record " + user.getUsername());
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        for (User user : users) {
            log.info("Retrieved user record: " + user.getUsername());
        }
        return users;
    }

    public void seedUsers() {
        userRepository.save(new User("Clayton", "pass"));
        userRepository.save(new User("John", "pass"));
        userRepository.save(new User("Chris", "pass"));
        userRepository.save(new User("Amanda", "pass"));
    }
}
