package edu.mum.mumsched.SectionRegistrationSubsystem;

import edu.mum.mumsched.model.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ISectionServiceImpl implements ISectionService {

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

    @Override
    public List<Section> findByBlockId(Integer blockId, Integer id) {
        return sectionRepository.findByBlockId(blockId, id);
    }
}
