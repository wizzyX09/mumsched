package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ICourseServiceImpl implements ICourseService {
    @Override
    public Course findById(Integer courseId) {
        return null;
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public void delete(Integer courseId) {

    }

    @Override
    public Course saveOrUpdate(Course course) {
        return null;
    }
}
