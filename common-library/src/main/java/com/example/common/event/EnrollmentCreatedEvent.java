package com.example.common.event;

/**
 * Published when a student successfully enrolls into a course.
 */
public record EnrollmentCreatedEvent(

        Long enrollmentId,
        Long userId,
        Long courseId
) {
}