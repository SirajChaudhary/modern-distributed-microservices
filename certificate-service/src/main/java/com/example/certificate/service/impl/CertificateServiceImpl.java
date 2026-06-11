package com.example.certificate.service.impl;

import com.example.certificate.entity.Certificate;
import com.example.certificate.exception.CertificateNotFoundException;
import com.example.certificate.producer.CertificateEventProducer;
import com.example.certificate.repository.CertificateRepository;
import com.example.certificate.request.CertificateRequest;
import com.example.certificate.response.CertificateResponse;
import com.example.certificate.service.CertificateService;
import com.example.certificate.service.CertificateValidationService;
import com.example.common.event.CertificateGeneratedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Handles certificate generation and retrieval operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl
        implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final CertificateValidationService certificateValidationService;

    private final CertificateEventProducer certificateEventProducer;

    @Override
    public CertificateResponse createCertificate(
            CertificateRequest request) {

        log.info("Creating certificate userId={} courseId={}", request.userId(), request.courseId());

        log.info("Calling USER-SERVICE userId={}", request.userId());

        certificateValidationService.validateUser(request.userId());

        log.info("Calling COURSE-SERVICE courseId={}", request.courseId());

        certificateValidationService.validateCourse(request.courseId());

        Certificate certificate = new Certificate();

        certificate.setUserId(request.userId());

        certificate.setCourseId(request.courseId());

        certificate.setCertificateNumber(UUID.randomUUID().toString());

        certificate.setIssuedAt(LocalDateTime.now());

        certificate.setCreatedAt(LocalDateTime.now());

        Certificate savedCertificate = certificateRepository.save(certificate);

        log.info("Certificate generated successfully id={}", savedCertificate.getId());

        CertificateGeneratedEvent event =
                new CertificateGeneratedEvent(
                        savedCertificate.getId(),
                        savedCertificate.getUserId(),
                        savedCertificate.getCourseId()
                );

        log.info("Publishing CertificateGeneratedEvent certificateId={}", savedCertificate.getId());

        certificateEventProducer.publishCertificateGeneratedEvent(event);

        return mapToResponse(savedCertificate);
    }

    @Override
    public CertificateResponse getCertificate(
            Long id) {

        log.info("Fetching certificate id={}", id);

        Certificate certificate =
                certificateRepository.findById(id)
                        .orElseThrow(() ->
                                new CertificateNotFoundException(id));

        return mapToResponse(certificate);
    }

    @Override
    public List<CertificateResponse> getAllCertificates() {

        log.info("Fetching all certificates");

        List<CertificateResponse> certificates =
                certificateRepository.findAll()
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        log.info("Retrieved {} certificates", certificates.size());

        return certificates;
    }

    private CertificateResponse mapToResponse(
            Certificate certificate) {

        return new CertificateResponse(
                certificate.getId(),
                certificate.getUserId(),
                certificate.getCourseId(),
                certificate.getCertificateNumber(),
                certificate.getIssuedAt()
        );
    }
}