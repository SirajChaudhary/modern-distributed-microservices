package com.example.enrollment.service.impl;

import com.example.common.event.EnrollmentCreatedEvent;
import com.example.enrollment.entity.Enrollment;
import com.example.enrollment.exception.EnrollmentNotFoundException;
import com.example.enrollment.producer.EnrollmentEventProducer;
import com.example.enrollment.repository.EnrollmentRepository;
import com.example.enrollment.request.EnrollmentRequest;
import com.example.enrollment.response.EnrollmentResponse;
import com.example.enrollment.service.EnrollmentService;
import com.example.enrollment.service.EnrollmentValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl
        implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentEventProducer enrollmentEventProducer;

    private final EnrollmentValidationService
            enrollmentValidationService;

    @Override
    public EnrollmentResponse createEnrollment(
            EnrollmentRequest request) {

        log.info("Creating enrollment userId={} courseId={}", request.userId(), request.courseId());

        log.info("Calling USER-SERVICE userId={}", request.userId());

        enrollmentValidationService.validateUser(request.userId());

        log.info("Calling COURSE-SERVICE courseId={}", request.courseId());

        enrollmentValidationService.validateCourse(request.courseId());

        Enrollment enrollment = new Enrollment();

        enrollment.setUserId(request.userId());
        enrollment.setCourseId(request.courseId());

        Enrollment savedEnrollment =
                enrollmentRepository.save(enrollment);

        log.info("Enrollment created successfully id={}", savedEnrollment.getId());

        EnrollmentCreatedEvent event =
                new EnrollmentCreatedEvent(
                        savedEnrollment.getId(),
                        savedEnrollment.getUserId(),
                        savedEnrollment.getCourseId()
                );

        log.info("Publishing EnrollmentCreatedEvent enrollmentId={}", savedEnrollment.getId());

        enrollmentEventProducer.publishEnrollmentCreatedEvent(event);

        return mapToResponse(savedEnrollment);
    }

    @Override
    public EnrollmentResponse getEnrollment(Long id) {

        log.info("Fetching enrollment id={}", id);

        Enrollment enrollment =
                enrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new EnrollmentNotFoundException(id));

        return mapToResponse(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {

        log.info("Fetching all enrollments");

        List<EnrollmentResponse> enrollments =
                enrollmentRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info("Retrieved {} enrollments", enrollments.size());

        return enrollments;
    }

    @Override
    public void deleteEnrollment(Long id) {

        log.info("Deleting enrollment id={}", id);

        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }

        enrollmentRepository.deleteById(id);

        log.info("Enrollment deleted successfully id={}", id);
    }

    private EnrollmentResponse mapToResponse(
            Enrollment enrollment) {

        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getUserId(),
                enrollment.getCourseId(),
                enrollment.getEnrolledAt()
        );
    }
}