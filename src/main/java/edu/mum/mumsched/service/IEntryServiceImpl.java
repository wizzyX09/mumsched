package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEntryServiceImpl implements IEntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry findById(Integer entryId) {
        return entryRepository.findOne(entryId);
    }

    @Override
    public void delete(Integer entryId) {
        entryRepository.delete(entryId);
    }

    @Override
    public void save(Entry entry) {
        entryRepository.save(entry);
    }

    @Override
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
