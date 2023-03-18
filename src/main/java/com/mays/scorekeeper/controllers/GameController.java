package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @PostMapping
    public ResponseEntity create() {
        return ResponseEntity.ok().build();
    }
}
