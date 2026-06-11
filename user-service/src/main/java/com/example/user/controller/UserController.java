package com.example.user.controller;

import com.example.common.constants.ApiMessages;
import com.example.common.response.ApiResponse;
import com.example.user.request.UserRequest;
import com.example.user.response.UserResponse;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {

        log.info("POST /api/v1/users - Creating user email={}", request.email());

        UserResponse response = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                ApiMessages.USER_CREATED,
                                response
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(
            @PathVariable Long id) {

        log.info("GET /api/v1/users/{} - Fetching user", id);

        UserResponse response = userService.getUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.USER_FETCHED,
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        log.info("GET /api/v1/users - Fetching all users");

        List<UserResponse> response = userService.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.USER_FETCHED,
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {

        log.info("PUT /api/v1/users/{} - Updating user", id);

        UserResponse response = userService.updateUser(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.USER_UPDATED,
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id) {

        log.info("DELETE /api/v1/users/{} - Deleting user", id);

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        ApiMessages.USER_DELETED,
                        null
                )
        );
    }
}