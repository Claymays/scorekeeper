package com.mays.scorekeeper.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * A mapping class for random api responses
 *
 * @author Clayton Mays
 */
@Data
public class RandomResponse {

    private List<Integer> responses;

    /**
     * A method for unpacking nested json properties
     * @param result the nested json property containing our search result
     */
    @JsonProperty("result")
    private void unpackData(Map<String, Object> result) {
        Map<String, Object> random = (Map<String, Object>)
                                    result.get("random");
        this.responses = (List<Integer>) random.get("data");
    }
}
