package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Block;

import java.util.List;

public interface IBlockService {
    public Block findById(Integer blockId);
    public void delete(Integer blockId);
    public void save(Block block);
    public List<Block> findAll();
}
