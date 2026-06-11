package com.example.enrollment.client.response;

/**
 * User details returned by User Service.
 */
public record UserClientResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role
) {
}