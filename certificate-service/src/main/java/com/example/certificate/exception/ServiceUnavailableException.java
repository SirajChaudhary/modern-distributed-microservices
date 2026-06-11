package com.example.certificate.exception;

/**
 * Thrown when a downstream service is unavailable.
 */
public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
}