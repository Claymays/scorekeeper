package com.mays.scorekeeper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RandomResponse {

    private List<Integer> responses;

    @JsonProperty("result")
    private void unpackData(Map<String, Object> result) {
        Map<String, Object> random = (Map<String, Object>) result.get("random");
        this.responses = (List<Integer>) random.get("data");
    }
}
