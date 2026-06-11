package com.example.enrollment.controller;

import com.example.common.constants.ApiMessages;
import com.example.common.response.ApiResponse;
import com.example.enrollment.request.EnrollmentRequest;
import com.example.enrollment.response.EnrollmentResponse;
import com.example.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs for enrollment management.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * Create a new enrollment.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponse>> createEnrollment(
            @Valid @RequestBody EnrollmentRequest request) {

        log.info("POST /api/v1/enrollments - Creating enrollment userId={} courseId={}", request.userId(), request.courseId());

        EnrollmentResponse response = enrollmentService.createEnrollment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                ApiMessages.ENROLLMENT_CREATED,
                                response
                        )
                );
    }

    /**
     * Get enrollment by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getEnrollment(
            @PathVariable Long id) {

        log.info("GET /api/v1/enrollments/{} - Fetching enrollment", id);

        EnrollmentResponse response = enrollmentService.getEnrollment(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.ENROLLMENT_FETCHED,
                        response
                )
        );
    }

    /**
     * Get all enrollments.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAllEnrollments() {

        log.info("GET /api/v1/enrollments - Fetching all enrollments");

        List<EnrollmentResponse> response = enrollmentService.getAllEnrollments();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.ENROLLMENT_FETCHED,
                        response
                )
        );
    }

    /**
     * Delete enrollment by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEnrollment(
            @PathVariable Long id) {

        log.info("DELETE /api/v1/enrollments/{} - Deleting enrollment", id);

        enrollmentService.deleteEnrollment(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.ENROLLMENT_DELETED,
                        null
                )
        );
    }
}