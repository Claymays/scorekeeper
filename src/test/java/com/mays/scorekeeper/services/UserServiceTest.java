package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("UserService Can: ")
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    @Transactional
    @DisplayName("Create New User")
    void create() {
        Optional<User> created = userService.create("Clayton", "password");
        Optional<User> duplicate = userService.create("Clayton", "password");

        assertFalse(duplicate.isPresent());
        assertTrue(created.isPresent());
        assertEquals(created.get(), userService.get(created.get().getId()).get());
    }

    @Test
    @DisplayName("Retrieve User Record")
    void get() {
        int expectedId = userService.create("Clayton", "pass").get().getId();
        int result = userService.get(expectedId).get().getId();
        assertEquals(expectedId, result);
    }

    @Test
    @DisplayName("Delete User Record")
    void delete() {
        User user = userService.create("Clayton", "pass").get();
        userService.delete(user.getId());
        assertFalse(userService.get(user.getId()).isPresent());
    }

    @Test
    @Transactional
    @DisplayName("Update User Record")
    void update() {
        User originalUser = userService.create("Clayton", "pass").get();
        User altUser = userService.get(originalUser.getId()).get();
        String newUsername = "not Clayton";
        altUser.setUsername(newUsername);

        userService.update(altUser);

        User postUpdateUser = userService.get(originalUser.getId()).get();

        assertEquals(newUsername, postUpdateUser.getUsername());
        assertEquals(altUser, postUpdateUser);
    }
}