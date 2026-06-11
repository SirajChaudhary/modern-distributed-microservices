package com.example.certificate.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request used to generate a certificate.
 */
public record CertificateRequest(

        @NotNull(message = "User Id is required")
        Long userId,

        @NotNull(message = "Course Id is required")
        Long courseId

) {
}