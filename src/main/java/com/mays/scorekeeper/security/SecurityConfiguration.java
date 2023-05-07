package com.mays.scorekeeper.security;

import com.mays.scorekeeper.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for web application
 *
 * @author Clayton Mays
 */
@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    /**
     * Adds the user service, and password encoder to server authentication context
     * @param auth the authentication manager being loaded into application context
     * @throws Exception when passed properties are not configured correctly
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Password encoder bean for application
     * @return encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a bean for authentication management
     * @return Authentication manager bean
     * @throws Exception when bean cannot be created
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configures web security to ignore resource and login paths
     * @param web application web security context
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                        "/css/**",
                        "/js/**",
                        "/login",
                        "/register",
                        "/profile",
                        "/api/user/login",
                        "/api/user/"
                )
                .and()
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS);
    }

    /**
     * Catches incoming http requests, prepending jwt auth filter, and denies unauthorized resources
     * @param http the http request
     * @throws Exception if a nested method throws an exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .anyRequest().anonymous()
                .and().formLogin().loginPage("/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .logout().logoutSuccessUrl("/login")
                .permitAll();
    }
}
