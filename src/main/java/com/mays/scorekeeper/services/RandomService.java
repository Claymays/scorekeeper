package com.mays.scorekeeper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mays.scorekeeper.entities.RandomConfig;
import com.mays.scorekeeper.entities.RandomRequest;
import com.mays.scorekeeper.entities.RandomResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * A service class for consuming requests to random.org.
 *
 * @author Clayton Mays
 */
@Data
@Service
@Slf4j
public class RandomService {
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private RandomConfig config;

    /**
     * A class constructor, that instantiates each class variable
     */
    @Autowired
    public RandomService(RandomConfig config) {
        this.webClient = WebClient.builder().build();
        this.mapper = new ObjectMapper();
        this.config = config;
    }

    /**
     * A method for generating coin flips, from random.org.
     * @return A RandomResponse object containing either heads or tails
     */
    public RandomResponse flipCoin() {
        Map<String, String> params = new HashMap<>();
        params.put("apiKey", config.getKey());
        params.put("n", "1");
        params.put("min", "1");
        params.put("max", "2");
        String json = "";
        RandomRequest request = new RandomRequest("generateIntegers", params);
        try {
            json = mapper.writeValueAsString(request);
        } catch(JsonProcessingException error) {
            log.error(error.getMessage());
        }

        return webClient.post()
                .uri(config.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(json))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(RandomResponse.class)
                .block();
    }

    /**
     * A method for rolling a variable number of six-sided die.
     * @param rolls the number of rolls needed.
     * @return A RandomResponse object containing the results of the rolls.
     */
    public RandomResponse rollDSix(String rolls) {
        Map<String, String> params = new HashMap<>();
        log.info(config.getKey());
        log.info(config.getUrl());
        params.put("apiKey", config.getKey());
        params.put("n", rolls);
        params.put("min", "1");
        params.put("max", "6");
        String json = "";
        RandomRequest request = new RandomRequest("generateIntegers", params);
        try {
            json = mapper.writeValueAsString(request);
        } catch (JsonProcessingException error) {
            log.error(error.getMessage());
        }

        return webClient.post()
                .uri(config.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(json))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(RandomResponse.class)
                .block();
    }
}
