package com.example.enrollment.client.response;

/**
 * Course details returned by Course Service.
 */
public record CourseClientResponse(
        Long id,
        String title,
        String description,
        Long instructorId,
        Integer durationHours,
        Boolean active
) {
}