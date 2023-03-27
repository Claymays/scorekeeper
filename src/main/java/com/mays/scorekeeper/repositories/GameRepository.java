package com.mays.scorekeeper.repositories;

import com.mays.scorekeeper.entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository interface for executing transactions pertaining to Game records
 *
 * @author Clayton Mays
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

}
