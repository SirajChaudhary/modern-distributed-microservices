package com.example.enrollment.service;

import com.example.enrollment.request.EnrollmentRequest;
import com.example.enrollment.response.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponse createEnrollment(
            EnrollmentRequest request);

    EnrollmentResponse getEnrollment(Long id);

    List<EnrollmentResponse> getAllEnrollments();

    void deleteEnrollment(Long id);
}