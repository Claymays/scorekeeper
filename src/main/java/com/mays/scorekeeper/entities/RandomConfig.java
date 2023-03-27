package com.mays.scorekeeper.entities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.random")
public class RandomConfig {
    private String url;
    private String key;
}
