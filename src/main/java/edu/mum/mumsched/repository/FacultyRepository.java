package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
