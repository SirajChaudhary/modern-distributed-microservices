package com.example.enrollment.service.impl;

import com.example.enrollment.client.CourseClient;
import com.example.enrollment.client.UserClient;
import com.example.enrollment.exception.ServiceUnavailableException;
import com.example.enrollment.service.EnrollmentValidationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Verifies that User Service and Course Service are available
 * and that the requested user and course exist before
 * processing enrollment requests.
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
public class EnrollmentValidationServiceImpl
        implements EnrollmentValidationService {

    private final UserClient userClient;

    private final CourseClient courseClient;

    /**
     * Verifies that the requested user exists and
     * User Service is available.
     */
    @Override
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public void validateUser(Long userId) {

        log.info("Validating user userId={}", userId);

        userClient.getUser(userId);
    }

    /**
     * Verifies that the requested course exists and
     * Course Service is available.
     */
    @Override
    @Retry(name = "courseService")
    @CircuitBreaker(name = "courseService", fallbackMethod = "courseFallback")
    public void validateCourse(Long courseId) {

        log.info("Validating course courseId={}", courseId);

        courseClient.getCourse(courseId);
    }

    /**
     * Executed when User Service remains unavailable
     * after configured retry and circuit breaker policies.
     */
    public void userFallback(Long userId, Exception exception) {

        log.error("User Service unavailable userId={}", userId, exception);

        throw new ServiceUnavailableException("User Service is currently unavailable");
    }

    /**
     * Executed when Course Service remains unavailable
     * after configured retry and circuit breaker policies.
     */
    public void courseFallback(Long courseId, Exception exception) {

        log.error("Course Service unavailable courseId={}", courseId, exception);

        throw new ServiceUnavailableException("Course Service is currently unavailable");
    }
}