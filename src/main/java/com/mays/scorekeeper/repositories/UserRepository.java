package com.mays.scorekeeper.repositories;

import com.mays.scorekeeper.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);
}