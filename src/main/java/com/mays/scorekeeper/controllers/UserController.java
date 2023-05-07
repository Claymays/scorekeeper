package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.services.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@NoArgsConstructor
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private final Gson gson = new Gson();

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Data
    protected static class UserRequestBody {
        String username;
        String password;
    }

    @GetMapping(value = {"/", "/{id}"})
    public ResponseEntity getUser(@PathVariable(name="id", required = false) Integer id) {
        if (id != null) {
            return ResponseEntity.ok(userService.get(id));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/login")
    public  ResponseEntity<UserDetails> getByUsername(@ModelAttribute UserRequestBody authUser) {
        Optional<User> optUser = userService.getByUsername(authUser.username);
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (passwordEncoder.matches(authUser.password, user.getPassword())) {
                UserDetails details = userService.loadUserByUsername(authUser.username);
                return ResponseEntity.ok(details);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            createUser(authUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDetails> createUser(@RequestBody UserRequestBody newUser) {
        if (userService.existsByUsername(newUser.username)) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = passwordEncoder.encode(newUser.password);
        Optional<User> user = userService.create(newUser.username, encryptedPassword);

        if (user.isPresent()) {
            UserDetails userDetails = userService.loadUserByUsername(newUser.username);
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}