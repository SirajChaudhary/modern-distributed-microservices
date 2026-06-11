package com.example.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Generates a unique key used by Redis Rate Limiter.
 *
 * The client IP address is used as the rate limiting key.
 *
 * Requests originating from the same IP address share
 * the same rate limit configuration.
 *
 * Redis stores request counters centrally, allowing
 * rate limiting to work consistently across multiple
 * Gateway instances.
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {

        return exchange -> Mono.just(
                exchange
                        .getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress()
        );
    }
}