package com.example.enrollment.response;

import java.time.LocalDateTime;

public record EnrollmentResponse(
        Long id,
        Long userId,
        Long courseId,
        LocalDateTime enrolledAt
) {
}