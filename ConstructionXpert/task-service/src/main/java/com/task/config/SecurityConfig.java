package com.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                           .requestMatchers("/api/tasks/**").hasAnyRole("ADMIN", "CUSTOMER")
//                        .requestMatchers(HttpMethod.POST, "/api/tasks").hasRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyRole("ADMIN", "CUSTOMER")
//                        .requestMatchers(HttpMethod.GET, "/api/tasks/project/**").hasAnyRole("CUSTOMER")
//                        .requestMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole( "CUSTOMER")
//                        .requestMatchers(HttpMethod.GET, "/api/tasks/**/exist").hasAnyRole("CUSTOMER")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
