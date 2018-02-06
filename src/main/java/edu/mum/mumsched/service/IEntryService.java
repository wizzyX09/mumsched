package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Block;
import edu.mum.mumsched.model.Entry;

import java.util.List;

public interface IEntryService {
    public Entry findById(Integer entryId);
    public void delete(Integer entryId);
    public void save(Entry entry);
    public List<Entry> findAll();
    public List<Entry> findAllOrdered();
    public List<Entry> findEntryWithoutSchedule();
    public List<Entry> findEntriesByBlocksContains(Block block);
}
