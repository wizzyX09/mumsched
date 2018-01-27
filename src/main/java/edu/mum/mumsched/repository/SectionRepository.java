package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    public Section findById(Integer id);
}
