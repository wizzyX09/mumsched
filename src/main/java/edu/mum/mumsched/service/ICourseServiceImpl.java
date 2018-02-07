package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Course;
import edu.mum.mumsched.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ICourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course findById(Integer courseId) {
        return courseRepository.findOne(courseId);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void delete(Integer courseId) {
        courseRepository.delete(courseId);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAllExcept(Integer courseId) {
        return courseRepository.findByIdNot(courseId);
    }

    @Override
    public List<Course> findDuplicates(String name, Integer courseId) {
        return courseRepository.findByNameAndIdIsNot(name, courseId);
    }
}
