package com.example.user.response;

import com.example.user.entity.UserRole;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {
}