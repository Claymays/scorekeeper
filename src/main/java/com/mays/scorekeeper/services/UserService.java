package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A business logic service class for User objects.
 */
@Data
@Service
public class UserService {
    private UserRepository userRepository;
    private GameService gameService;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository autowired UserRepository
     */
    @Autowired
    public UserService(UserRepository userRepository, GameService gameService) {
        this.userRepository = userRepository;
        this.gameService = gameService;
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
             return Optional.empty();
         }
        return Optional.of(userRepository.save(new User(username, password)));
    }

    /**
     * Retrieve a User record from the database.
     *
     * @param id the id to search for
     * @return a user if found
     */
    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Delete a User record
     *
     * @param id the id of the user to be deleted.
     */
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Update a User record
     *
     * @param user a new User record to replace the old one
     */
    public void update(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void seedUsers() {
        userRepository.save(new User("Clayton", "pass"));
        userRepository.save(new User("John", "pass"));
        userRepository.save(new User("Chris", "pass"));
        userRepository.save(new User("Amanda", "pass"));
    }
}
