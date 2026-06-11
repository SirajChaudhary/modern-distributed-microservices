package com.example.certificate.controller;

import com.example.certificate.request.CertificateRequest;
import com.example.certificate.response.CertificateResponse;
import com.example.certificate.service.CertificateService;
import com.example.common.response.ApiResponse;
import com.example.common.response.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<ApiResponse<CertificateResponse>>
    createCertificate(@Valid @RequestBody CertificateRequest request) {

        log.info("POST /api/v1/certificates - Creating certificate");

        CertificateResponse response = certificateService.createCertificate(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Certificate generated successfully",
                                response
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>>
    getCertificate(
            @PathVariable Long id) {

        log.info("GET /api/v1/certificates/{} - Fetching certificate", id);

        CertificateResponse response =
                certificateService.getCertificate(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Certificate fetched successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificateResponse>>>
    getAllCertificates() {

        log.info("GET /api/v1/certificates - Fetching all certificates");

        List<CertificateResponse> response =
                certificateService.getAllCertificates();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Certificates fetched successfully",
                        response
                )
        );
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