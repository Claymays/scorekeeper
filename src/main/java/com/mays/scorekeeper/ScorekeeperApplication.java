package com.mays.scorekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Top level spring boot application entry point
 *
 * @author Clayton Mays
 */
@SpringBootApplication
public class ScorekeeperApplication {

    /**
     * Main method for application. Spins up server, and serves application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ScorekeeperApplication.class, args);
    }
}
