package edu.mum.mumsched.SectionRegistrationSubsystem;

import edu.mum.mumsched.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SectionRepository extends JpaRepository<Section, Integer> {
    public Section findById(Integer id);

    @Query("select s from Section s where block.id = :blockId and s.id <> :id")
    public List<Section> findByBlockId(@Param("blockId") Integer blockId, @Param("id") Integer id);
}
