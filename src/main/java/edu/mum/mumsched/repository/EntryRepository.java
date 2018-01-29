package edu.mum.mumsched.repository;


import edu.mum.mumsched.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("entryRepository")
public interface EntryRepository extends JpaRepository<Entry, Integer> {
	public Entry findEntryByEntryName(String name);
    @Query("from Entry en where en.schedule.id is null")
	List<Entry> findEntryWithoutSchedule();
}
