package com.example.enrollment.service;

public interface EnrollmentValidationService {

    void validateUser(Long userId);
    void validateCourse(Long courseId);
}
