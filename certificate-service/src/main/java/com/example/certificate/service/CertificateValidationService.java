package com.example.certificate.service;

/**
 * Validates downstream service dependencies required for certificate generation.
 */
public interface CertificateValidationService {

    void validateUser(Long userId);
    void validateCourse(Long courseId);
}