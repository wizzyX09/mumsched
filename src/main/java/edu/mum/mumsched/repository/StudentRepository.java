package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findById(Integer id);
    public List<Student> findAllByUserNull();
}
