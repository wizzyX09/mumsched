package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    public Student findById(Integer id);

    public Student findByEmail(String email);
    public Student findByUser(User user);
    public List<Student> findAllByUserIs(User user);
}
