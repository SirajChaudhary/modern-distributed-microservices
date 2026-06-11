package com.example.certificate.service;

import com.example.certificate.request.CertificateRequest;
import com.example.certificate.response.CertificateResponse;

import java.util.List;

/**
 * Service contract for certificate management.
 */
public interface CertificateService {

    CertificateResponse createCertificate(CertificateRequest request);

    CertificateResponse getCertificate(Long id);

    List<CertificateResponse> getAllCertificates();
}