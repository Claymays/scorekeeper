package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Game Service Can:")
class GameServiceTest {

    @Autowired
    GameService gameService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Can Create Game")
    void create() {
        Game expected = gameService.create(new Game("test")).get();
        Game result = gameService.get(expected.getId()).get();
        assertEquals(expected.getName(), result.getName());
    }

    @Test
    @DisplayName("Can Retrieve Game Record")
    void get() {
        Game game = gameService.create(new Game("test")).get();

    }

    @Test
    @DisplayName("Can Update Game Record")
    void update() {
        Game original = gameService.create(new Game("test")).get();
        Game updated = gameService.get(original.getId()).get();

        String newName = "updated name";
        updated.setName(newName);
        gameService.update(updated);

        Game postUpdate = gameService.get(original.getId()).get();

        assertEquals(newName, postUpdate.getName());
        assertEquals(updated, postUpdate);
        assertNotEquals(original.getName(), postUpdate.getName());
    }

    @Test
    @DisplayName("Can Delete Game Record")
    void delete() {
        Game game = gameService.create(new Game("test")).get();
        gameService.delete(game.getId());

        assertFalse(gameService.get(game.getId()).isPresent());
    }
}