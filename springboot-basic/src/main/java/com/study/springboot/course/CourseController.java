package com.study.springboot.course;

import com.study.springboot.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    TopicRepository topicRepository;

    @GetMapping("/topics/{topicId}/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping ("/topics/{topicId}/courses/{courseId}")
    public Optional<Course> getCourse(@PathVariable Long topicId,
                                      @PathVariable Long courseId) {
        return courseService.getCourse(topicId);
    }

    @PostMapping("/topics/{topicId}/courses")
    public void addCourse(@PathVariable Long topicId,
                          @RequestBody Course course) {
        course.setTopic(topicRepository.findById(topicId).get());
        courseService.addCourse(course);
    }

    @PutMapping ("/topics/{topicId}/courses/{courseId}")
    public void updateCourse(@PathVariable Long topicId,
                             @RequestBody Course course,
                             @PathVariable Long courseId) {
        course.setTopic(topicRepository.findById(topicId).get());
        courseService.updateCourse(course);
    }

    @DeleteMapping ("/topics/{id}/courses/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }


}
