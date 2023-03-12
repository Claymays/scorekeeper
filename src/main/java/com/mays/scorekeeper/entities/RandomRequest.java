package com.mays.scorekeeper.entities;

import lombok.Data;

@Data
public class RandomRequest {
    private String jsonrpc;
    private String method;
    private Object params;
    private int id;

    public RandomRequest(String method, Object params) {
        this.jsonrpc = "2.0";
        this.id = 1010;
        this.method = method;
        this.params = params;
    }
}
