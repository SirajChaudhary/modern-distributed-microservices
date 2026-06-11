package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway acts as the single entry point to the microservices ecosystem.
 *
 * Future responsibilities:
 * - Request Routing
 * - JWT Validation
 * - Role Based Access Control (RBAC)
 * - Correlation ID Generation
 * - Request/Response Logging
 * - Rate Limiting
 * - Global Error Handling
 */
@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
