package com.example.certificate.client;

import com.example.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * OpenFeign client for Course Service.
 */
@FeignClient(name = "course-service")
public interface CourseClient {

    @GetMapping("/api/v1/courses/{id}")
    ApiResponse<Object> getCourse(@PathVariable Long id);
}