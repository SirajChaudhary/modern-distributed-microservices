package com.example.gateway.filter;

import com.example.common.correlation.CorrelationIdConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Generates and propagates a CorrelationId for every incoming request.
 *
 * If the request does not already contain an X-Correlation-Id header,
 * a new UUID-based CorrelationId is generated.
 *
 * The CorrelationId is then:
 * - Added to the downstream request headers
 * - Propagated across all microservices
 * - Added to the response headers returned to the client
 *
 * This enables end-to-end request tracking and log correlation across
 * the distributed microservices ecosystem.
 *
 * Request flow:
 *
 * Client (Postman)
 *        |
 *        | (no X-Correlation-Id)
 *        v
 * Gateway
 *        |
 *        | generates CorrelationId
 *        v
 * Downstream Services
 *        |
 *        v
 * Response
 *        |
 *        | X-Correlation-Id: <generated-id>
 *        v
 * Client (Postman)
 */
@Slf4j
@Component
public class CorrelationIdFilter
        implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String correlationId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(CorrelationIdConstants.CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {

            correlationId = UUID.randomUUID().toString();

            log.debug("Generated correlationId={}", correlationId);
        }

        ServerHttpRequest request =
                exchange.getRequest()
                        .mutate()
                        .header(CorrelationIdConstants.CORRELATION_ID, correlationId)
                        .build();

        exchange.getResponse()
                .getHeaders()
                .add(CorrelationIdConstants.CORRELATION_ID, correlationId);

        return chain.filter(
                exchange.mutate()
                        .request(request)
                        .build()
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}