package com.mays.scorekeeper;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Configuration class for spring boot application
 *
 * @author Clayton Mays
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Automatically detects configuration based on annotations,
     * @param application the current application
     * @return Application builder containing context resources
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ScorekeeperApplication.class);
    }

}
