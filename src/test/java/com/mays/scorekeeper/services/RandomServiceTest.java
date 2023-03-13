package com.mays.scorekeeper.services;

import com.mays.scorekeeper.entities.RandomResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("RandomService Can: ")
@Slf4j
public class RandomServiceTest {
    @Autowired private RandomService randomService;

    @Test
    @DisplayName("Flip a coin")
    void flipCoin() {
        int heads = 1;
        int tails = 2;
        List<RandomResponse> responses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            responses.add(randomService.flipCoin());
        }
        log.info("Checking for heads or tails");
        for (RandomResponse response : responses) {
            int result = response.getResponses().get(0);
            log.info(String.valueOf(result));
            assertTrue(result == heads || result == tails);
        }
    }

    @Test
    @DisplayName("Roll 6 Sided Die")
    void rollDSix() {
        int lowerRange = 1;
        int upperRange = 6;
        List<Integer> results = randomService.rollDSix("10").getResponses();
        log.info("checking for successful die rolls in " + results);
        for (Integer roll : results) {
            assertTrue(roll >= lowerRange && roll <= upperRange);
        }
    }
}
