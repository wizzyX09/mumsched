package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Course;

import java.util.List;

public interface ICourseService {
    public Course findById(Integer courseId);
    public List<Course> findAll();
    public void delete(Integer courseId);
    public Course saveOrUpdate(Course course);
}