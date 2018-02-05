package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    public Faculty findFacultyByEmail(String email);
}
