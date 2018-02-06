package edu.mum.mumsched.SectionRegistrationSubsystem;

import edu.mum.mumsched.model.Section;

import java.util.List;

public interface ISectionRegistrationSubsystem {
    public List<Section> sectionList();
    public List<Section> findByBlockId(Integer blockId, Integer id);
    public void save(Section section);
    public Section findById(Integer id);
    public void delete(Integer id);
}
