package com.example.enrollment.client;

import com.example.common.response.ApiResponse;
import com.example.enrollment.client.response.CourseClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for retrieving course information from Course Service.
 */
@FeignClient(name = "COURSE-SERVICE")
public interface CourseClient {

    /**
     * Retrieve course details by course id.
     */
    @GetMapping("/api/v1/courses/{id}")
    ApiResponse<CourseClientResponse> getCourse(@PathVariable Long id);
}