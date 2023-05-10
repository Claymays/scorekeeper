package com.mays.scorekeeper.security;

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
public class SecurityConfiguration  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth
                        .antMatchers("/register", "/").permitAll()
                        .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .defaultSuccessUrl("/profile")
                        .permitAll())
                .logout(logout -> {
                    logout
                    .logoutUrl("/logout") // The URL that triggers the logout process
                            .logoutSuccessUrl("/") // The URL to redirect after successful logout
                            .invalidateHttpSession(true) // Invalidate the HttpSession
                            .deleteCookies("JSESSIONID") // Delete the JSESSIONID cookie
                            .permitAll();
                })
                .build();


    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
