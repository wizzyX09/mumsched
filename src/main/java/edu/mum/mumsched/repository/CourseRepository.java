package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Course findByCode(String code);

    List<Course> findByIdNot(Integer courseId);
}
