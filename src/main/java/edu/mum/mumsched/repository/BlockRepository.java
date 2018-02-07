package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    public Block findBlockByBlockName(String name);
    public List<Block> findAllByOrderByStartDateAsc();
    public Block findBlockByBlockNameAndSequenceNumber(String blockName, Integer seqNum);
}
