package com.example.course.response;

public record CourseResponse(
        Long id,
        String title,
        String description,
        Long instructorId,
        Integer durationHours,
        Boolean active
) {
}