package com.example.enrollment.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request used to create an enrollment.
 */
public record EnrollmentRequest(

        @NotNull(message = "User Id is required")
        Long userId,

        @NotNull(message = "Course Id is required")
        Long courseId

) {
}