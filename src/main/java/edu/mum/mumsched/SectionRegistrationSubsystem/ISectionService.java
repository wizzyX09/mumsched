package edu.mum.mumsched.SectionRegistrationSubsystem;

import edu.mum.mumsched.model.Section;

import java.util.List;

interface ISectionService {
    public Section findById(Integer sectionId);
    public void delete(Integer sectionId);
    public void save(Section section);
    public List<Section> findAll();

    public List<Section> findByBlockId(Integer blockId, Integer id);

}
