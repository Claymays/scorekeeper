package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.repositories.GameRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@NoArgsConstructor
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public Optional<Game> create(Game game) {
        return Optional.of(gameRepository.save(game));
    }

    public Game get() {
        return new Game();
    }

    public void update() {

    }

    public void delete() {

    }


}
