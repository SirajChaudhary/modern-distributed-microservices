package com.example.course.service;

import com.example.course.request.CourseRequest;
import com.example.course.response.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourse(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    void deleteCourse(Long id);
}