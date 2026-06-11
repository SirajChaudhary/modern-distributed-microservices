package com.example.certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point for Certificate Service.
 *
 * This service is responsible for:
 *
 * - Generating certificates
 * - Retrieving certificate details
 * - Publishing CertificateGeneratedEvent to Kafka
 * - Validating User Service and Course Service availability
 *   through OpenFeign clients
 */
@SpringBootApplication
@EnableFeignClients
public class CertificateServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CertificateServiceApplication.class, args);
    }
}