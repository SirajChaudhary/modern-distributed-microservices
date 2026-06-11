package com.example.common.event;

/**
 * Published when a certificate is successfully generated.
 */
public record CertificateGeneratedEvent(

        Long certificateId,
        Long userId,
        Long courseId
) {
}