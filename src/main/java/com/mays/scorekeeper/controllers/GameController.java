package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.Friend;
import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * A REST api controller for Game records
 */
@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;

    /**
     * A method for creating Game records
     * @return response entity containing newly created Game record
     */
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Game> create() {
        return ResponseEntity.ok(new Game());
    }
}
