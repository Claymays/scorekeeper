package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.repositories.GameRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * A DAO service for Game records.
 *
 * @author Clayton Mays
 */
@Data
@NoArgsConstructor
@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    /**
     * Create a game record stored in the database, and returned to caller.
     *
     * @param game the game
     * @return the optional
     */
    public Optional<Game> create(Game game) {
        return Optional.of(gameRepository.save(game));
    }

    /**
     * Retrieves Game record from database.
     *
     * @param id the id to query for
     * @return the record found
     */
    public Optional<Game> get(Integer id) {
        return gameRepository.findById(id);
    }

    /**
     * Update a Game record in the database.
     *
     * @param game new version of record to be stored
     */
    public void update(Game game) {
        gameRepository.save(game);
    }

    /**
     * Delete a Game record.
     *
     * @param id query to delete
     */
    public void delete(Integer id) {
        gameRepository.deleteById(id);
    }
}
