package com.mays.scorekeeper.entities;

import lombok.Data;

/**
 * A mapping class for requests to random api
 *
 * @author Clayton Mays
 */
@Data
public class RandomRequest {
    private String jsonrpc;
    private String method;
    private Object params;
    private int id;

    /**
     * A partially qualified class constructor,
     *  that takes an http method, and query parameters
     * @param method http method to execute
     * @param params query parameters for the request
     */
    public RandomRequest(String method, Object params) {
        this.jsonrpc = "2.0";
        this.id = 1010;
        this.method = method;
        this.params = params;
    }
}
