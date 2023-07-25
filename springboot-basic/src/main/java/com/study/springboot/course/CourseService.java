package com.study.springboot.course;

import com.study.springboot.course.Course;
import com.study.springboot.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            System.out.println(course.getTopic());
            courses.add(course);
        });
        return courses;
    }

    public Optional<Course> getCourse(Long topicId) {
        return courseRepository.findById(topicId);

    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
