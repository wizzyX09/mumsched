package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseUserRepository<Student> {
}
