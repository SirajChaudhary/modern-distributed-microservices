package com.example.enrollment.client;

import com.example.common.response.ApiResponse;
import com.example.enrollment.client.response.UserClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for retrieving user information from User Service.
 */
@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    /**
     * Retrieve user details by user id.
     */
    @GetMapping("/api/v1/users/{id}")
    ApiResponse<UserClientResponse> getUser(@PathVariable Long id);
}