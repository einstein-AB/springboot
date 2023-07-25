package com.study.springboot.course;

import com.study.springboot.course.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    public List<Course> findByTopicId(Long topicId);
}
