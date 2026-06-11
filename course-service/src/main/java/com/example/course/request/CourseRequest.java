package com.example.course.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request used to create a course.
 */
public record CourseRequest(

        @NotBlank(message = "Course title is required")
        @Size(max = 100, message = "Course title cannot exceed 100 characters")
        String title,

        @NotBlank(message = "Course description is required")
        @Size(max = 500, message = "Course description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Instructor Id is required")
        Long instructorId,

        @NotNull(message = "Course duration is required")
        @Min(value = 1, message = "Course duration must be greater than zero")
        Integer durationHours

) {
}