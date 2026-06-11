package com.example.course.controller;

import com.example.common.constants.ApiMessages;
import com.example.common.response.ApiResponse;
import com.example.course.request.CourseRequest;
import com.example.course.response.CourseResponse;
import com.example.course.service.CourseService;
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
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CourseRequest request) {

        log.info("POST /api/v1/courses - Creating course title={}", request.title());

        CourseResponse response = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, ApiMessages.COURSE_CREATED, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourse(
            @PathVariable Long id) {

        log.info("GET /api/v1/courses/{} - Fetching course", id);

        CourseResponse response = courseService.getCourse(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, ApiMessages.COURSE_FETCHED, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {

        log.info("GET /api/v1/courses - Fetching all courses");

        List<CourseResponse> response = courseService.getAllCourses();

        return ResponseEntity.ok(
                new ApiResponse<>(true, ApiMessages.COURSE_FETCHED, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequest request) {

        log.info("PUT /api/v1/courses/{} - Updating course", id);

        CourseResponse response = courseService.updateCourse(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, ApiMessages.COURSE_UPDATED, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(
            @PathVariable Long id) {

        log.info("DELETE /api/v1/courses/{} - Deleting course", id);

        courseService.deleteCourse(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, ApiMessages.COURSE_DELETED, null));
    }
}