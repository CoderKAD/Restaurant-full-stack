package com.restaurantapp.demo.config;

import com.restaurantapp.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enables Spring Security’s web security support
@EnableMethodSecurity // Allows use of @PreAuthorize and other method-level security annotations
public class SecurityConfig {

    // JWT filter to validate tokens for each request
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Service to load user details from DB
    private final UserDetailsService userDetailsService;

    // Constructor injection of dependencies
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    // Bean for password encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder hashes passwords securely
        return new BCryptPasswordEncoder();
    }

    // Bean for authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        // DaoAuthenticationProvider uses UserDetailsService to load user data
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        // Set the password encoder to check hashed passwords
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Bean for authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Provides the AuthenticationManager used by Spring Security
        return config.getAuthenticationManager();
    }

    // Configure security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (because we use JWT stateless authentication)
                .csrf(csrf -> csrf.disable())

                // Allow H2 console frames (for dev/testing)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // Set session management to stateless (no HTTP session)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define which endpoints are public and which need roles
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to access auth endpoints and H2 console
                        .requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
                        // Only ADMIN can access /api/admin/**
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // USER and ADMIN can access /api/user/**
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        // All other requests need authentication
                        .anyRequest().authenticated()
                )

                // Add JWT filter before Spring Security's username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the configured filter chain
        return http.build();
    }
}