package com.example.certificate.exception;

import com.example.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleCertificateNotFoundException(CertificateNotFoundException exception) {

        log.error("Certificate not found", exception);

        ErrorResponse response =
                new ErrorResponse(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse>
    handleServiceUnavailableException(
            ServiceUnavailableException exception) {

        log.error("Downstream service unavailable", exception);

        ErrorResponse response =
                new ErrorResponse(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(Exception exception) {

        log.error("Unexpected exception", exception);

        ErrorResponse response =
                new ErrorResponse(
                        false,
                        "An unexpected error occurred",
                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        log.error("Validation failed {}", errors);

        ErrorResponse response =
                new ErrorResponse(
                        false,
                        errors.toString(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}