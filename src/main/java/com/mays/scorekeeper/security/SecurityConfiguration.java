package com.mays.scorekeeper.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for web application
 *
 * @author Clayton Mays
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth
                        .antMatchers("/register", "/create-user", "/", "/static/**").permitAll()
                        .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/profile")
                        .permitAll())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
