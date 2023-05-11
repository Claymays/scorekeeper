package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A business logic service class for User objects.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with name:" + username + "not found"));

        String password = user.getPassword();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.
                userdetails.User(username, password, authorities);
    }

    /**
     * Creates a new User record if it does not exist.
     *
     * @param username the username
     * @param password the password
     * @return the created User object, or blank if unsuccessful
     */
    public Optional<User> create(String username, String password) {
        if (existsByUsername(username)) {
             log.warn("Attempted to create a user record with username: "
                     + username + " but name already exists");
             return Optional.empty();
        }
        log.info("Creating user with name: {}", username);
        String encodedPassword = passwordEncoder.encode(password);
        return Optional.of(userRepository.save(
                new User(username, encodedPassword)));
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
            log.warn("Attempted to retrieve user record with ID: " +
                    id + " but no record found");
        } else {
            log.info("Retrieved user record {}", user.get().getUsername());
        }
        return user;
    }

    /**
     * Retrieve a User record by username property
     * @param username the username to query for
     * @return User record if found, empty Optional object if not
     */
    public Optional<User> getByUsername(String username) {
        Optional<User> user = userRepository.findOneByUsername(username);
        if (user.isEmpty()) {
            log.warn("Attempted to retrieve user record with username: "
                    + username + " but no record found");
        } else {
            log.info("Retrieved user record {}", username);
        }
        return user;
    }

    /**
     * Delete a User record
     *
     * @param id the id of the user to be deleted.
     */
    public void delete(Integer id) {
        log.info("Deleting User record with id: {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Update a User record
     *
     * @param user a new User record to replace the old one
     */
    public void update(User user) {
        log.info("Updating user record {}", user.getUsername());
        userRepository.save(user);
    }

    /**
     * A method for retrieving all User records.
     * @return each user record
     */
    public List<User> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        for (User user : users) {
            log.info("Retrieved user record: {}", user.getUsername());
        }
        return users;
    }

    /**
     * A method for checking availability of a username.
     * @param username the new username to check against.
     * @return false if the username does not exist in the database.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Method for accessing current user from session
     * @return authenticated session user
     */
    public Optional<User> getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            return getByUsername(username);
        } else {
            return Optional.empty();
        }
    }
}
