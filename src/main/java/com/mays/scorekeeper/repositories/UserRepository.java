package com.mays.scorekeeper.repositories;

import com.mays.scorekeeper.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository interface for executing transactions pertaining to User records
 *
 * @author Clayton Mays
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    /**
     * A method for checking viability of new users
     * @param username username to check against
     * @return false if username already exists
     */
    boolean existsByUsername(String username);

    /**
     * A method for querying by individual username property
     * @param username username to query for
     * @return Optional of User record if found
     */
    Optional<User> findOneByUsername(String username);
}