package edu.mum.mumsched.SectionRegistrationSubsystem;

import edu.mum.mumsched.model.Course;
import edu.mum.mumsched.model.Section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SectionRegistrationFacade implements ISectionRegistrationSubsystem {

    @Autowired
    ISectionService iSectionService;
    @Override
    public List<Section> findAll() {
        return iSectionService.findAll();
    }

    @Override
    public List<Section> findByBlockId(Integer blockId, Integer id) {
        return iSectionService.findByBlockId(blockId,id);
    }

    @Override
    public void save(Section section) {
        iSectionService.save(section);
    }

    @Override
    public Section findById(Integer id) {
        return iSectionService.findById(id);
    }

    @Override
    public void delete(Integer id) {
        iSectionService.delete(id);
    }

    @Override
    public List<Section> findByCourse(Course course) {
        return iSectionService.findByCourse(course);
    }
}
