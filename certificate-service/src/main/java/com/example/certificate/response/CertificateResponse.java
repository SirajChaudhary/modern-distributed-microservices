package com.example.certificate.response;

import java.time.LocalDateTime;

/**
 * Response returned for certificate operations.
 */
public record CertificateResponse(

        Long id,
        Long userId,
        Long courseId,
        String certificateNumber,
        LocalDateTime issuedAt
) {
}