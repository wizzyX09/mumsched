package edu.mum.mumsched.repository;


import edu.mum.mumsched.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("entryRepository")
public interface EntryRepository extends JpaRepository<Entry, Integer> {
	public Entry findEntryByEntryName(String name);

}
