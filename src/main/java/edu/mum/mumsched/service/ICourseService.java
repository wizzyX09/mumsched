package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Course;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICourseService {
    public Course findById(Integer courseId);
    public List<Course> findAll();
    public void delete(Integer courseId);
    public Course save(Course course);
    public List<Course> findAllExcept(Integer courseId);
    public List<Course> findDuplicates(String name, Integer courseId);
}