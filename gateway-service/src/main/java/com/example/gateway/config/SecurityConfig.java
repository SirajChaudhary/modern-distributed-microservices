package com.example.gateway.config;

import com.example.gateway.security.KeycloakRealmRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configures JWT authentication and role-based authorization
 * for API Gateway.
 *
 * JWT tokens are validated using Keycloak.
 *
 * Roles are extracted from:
 *
 * realm_access.roles
 *
 * Example:
 *
 * STUDENT
 * ADMIN
 * INSTRUCTOR
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        JwtAuthenticationConverter jwtConverter =
                new JwtAuthenticationConverter();

        jwtConverter.setJwtGrantedAuthoritiesConverter(
                new KeycloakRealmRoleConverter()
        );

        return http

                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange

                        .pathMatchers(
                                "/actuator/**"
                        ).permitAll()

                        .pathMatchers(
                                "/api/v1/users/**"
                        ).hasRole("ADMIN")

                        .pathMatchers(
                                "/api/v1/courses/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "INSTRUCTOR"
                        )

                        .pathMatchers(
                                "/api/v1/enrollments/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "STUDENT"
                        )

                        .pathMatchers(
                                "/api/v1/certificates/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "STUDENT"
                        )

                        .anyExchange()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(
                                                jwtConverter
                                        )
                                )
                        )
                )

                .build();
    }
}