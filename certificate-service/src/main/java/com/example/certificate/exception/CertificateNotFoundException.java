package com.example.certificate.exception;

/**
 * Thrown when a certificate is not found.
 */
public class CertificateNotFoundException
        extends RuntimeException {

    public CertificateNotFoundException(Long id) {

        super("Certificate not found with id: " + id);
    }
}