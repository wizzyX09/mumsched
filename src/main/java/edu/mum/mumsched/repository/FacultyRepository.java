package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends BaseUserRepository<Faculty> {

}
