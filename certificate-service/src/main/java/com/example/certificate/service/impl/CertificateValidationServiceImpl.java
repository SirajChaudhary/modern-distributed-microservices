package com.example.certificate.service.impl;

import com.example.certificate.client.CourseClient;
import com.example.certificate.client.UserClient;
import com.example.certificate.exception.ServiceUnavailableException;
import com.example.certificate.service.CertificateValidationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Verifies that User Service and Course Service are available
 * and that the requested user and course exist before
 * processing certificate requests.
 *
 * Fault tolerance is implemented using Resilience4j:
 *
 * - Retry
 *   Automatically retries transient failures.
 *
 * - Circuit Breaker
 *   Prevents repeated calls to unhealthy services and
 *   fails fast when the failure threshold is exceeded.
 *
 * - Fallback
 *   Returns a meaningful business exception when a
 *   downstream service is unavailable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateValidationServiceImpl
        implements CertificateValidationService {

    private final UserClient userClient;

    private final CourseClient courseClient;

    /**
     * Validates that the requested user exists.
     */
    @Override
    @Retry(name = "userService")
    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "userFallback"
    )
    public void validateUser(Long userId) {

        log.info("Validating user userId={}", userId);

        userClient.getUser(userId);
    }

    /**
     * Validates that the requested course exists.
     */
    @Override
    @Retry(name = "courseService")
    @CircuitBreaker(
            name = "courseService",
            fallbackMethod = "courseFallback"
    )
    public void validateCourse(Long courseId) {

        log.info("Validating course courseId={}", courseId);

        courseClient.getCourse(courseId);
    }

    /**
     * Fallback method executed when User Service
     * remains unavailable.
     */
    public void userFallback(
            Long userId,
            Exception exception) {

        log.error("User Service unavailable userId={}", userId, exception);

        throw new ServiceUnavailableException("User Service is currently unavailable");
    }

    /**
     * Fallback method executed when Course Service
     * remains unavailable.
     */
    public void courseFallback(
            Long courseId,
            Exception exception) {

        log.error("Course Service unavailable courseId={}", courseId, exception);

        throw new ServiceUnavailableException("Course Service is currently unavailable");
    }
}