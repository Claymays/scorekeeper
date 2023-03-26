package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@NoArgsConstructor
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/{id}"})
    public ResponseEntity getUser(@PathVariable(name="id", required = false) Integer id) {
        if (id != null) {
            return ResponseEntity.ok(userService.get(id));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }
}