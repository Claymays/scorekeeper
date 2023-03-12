package com.mays.scorekeeper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mays.scorekeeper.entities.RandomRequest;
import com.mays.scorekeeper.entities.RandomResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Data
@Service
@Slf4j
public class RandomService {
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private final String url;
    private final String key;

    public RandomService() {
        this.webClient = WebClient.builder().build();
        this.mapper = new ObjectMapper();
        this.url = "https://api.random.org/json-rpc/4/invoke";
        this.key = "3e63b8c8-8431-4b40-92c7-3c7b1c7fc52a";
    }

    public RandomResponse flipCoin() {
        Map<String, String> params = new HashMap<>();
        params.put("apiKey", key);
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
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(json))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(RandomResponse.class)
                .block();
    }

    public RandomResponse rollDSix(String rolls) {
        Map<String, String> params = new HashMap<>();
        params.put("apiKey", key);
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
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(json))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(RandomResponse.class)
                .block();
    }
}
