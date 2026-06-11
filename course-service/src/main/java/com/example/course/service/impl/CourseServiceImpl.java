package com.example.course.service.impl;

import com.example.course.entity.Course;
import com.example.course.exception.CourseNotFoundException;
import com.example.course.repository.CourseRepository;
import com.example.course.request.CourseRequest;
import com.example.course.response.CourseResponse;
import com.example.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {

        log.info("Creating course title={}", request.title());

        Course course = new Course();

        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setInstructorId(request.instructorId());
        course.setDurationHours(request.durationHours());

        Course savedCourse = courseRepository.save(course);

        log.info("Course created successfully id={}", savedCourse.getId());

        return mapToResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourse(Long id) {

        log.info("Fetching course id={}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        return mapToResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        log.info("Fetching all courses");

        List<CourseResponse> courses = courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Retrieved {} courses", courses.size());

        return courses;
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {

        log.info("Updating course id={}", id);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setInstructorId(request.instructorId());
        course.setDurationHours(request.durationHours());

        Course updatedCourse = courseRepository.save(course);

        log.info("Course updated successfully id={}", updatedCourse.getId());

        return mapToResponse(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {

        log.info("Deleting course id={}", id);

        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }

        courseRepository.deleteById(id);

        log.info("Course deleted successfully id={}", id);
    }

    private CourseResponse mapToResponse(Course course) {

        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId(),
                course.getDurationHours(),
                course.getActive()
        );
    }
}