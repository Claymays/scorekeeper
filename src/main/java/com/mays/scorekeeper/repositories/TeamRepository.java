package com.mays.scorekeeper.repositories;

import com.mays.scorekeeper.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository interface for executing transactions pertaining to Team records
 *
 * @author Clayton Mays
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
}