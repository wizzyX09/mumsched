package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISectionServiceImpl implements ISectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public Section findById(Integer sectionId) { return sectionRepository.findOne(sectionId); }

    @Override
    public void delete(Integer sectionId) {
        sectionRepository.delete(sectionId);
    }

    @Override
    public void save(Section entry) {
        sectionRepository.save(entry);
    }

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }
}
