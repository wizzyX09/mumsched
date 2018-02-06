package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Role;

import java.util.List;

public interface IRoleService {
    public List<Role> findAll();
    public Role findById(Integer id);
    public List<Role> findAllExcept(String role1, String role2);
}
