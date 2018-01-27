package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Section;

import java.util.List;

public interface ISectionService {
    public Section findById(Integer sectionId);
    public void delete(Integer sectionId);
    public void save(Section section);
    public List<Section> findAll();
}
