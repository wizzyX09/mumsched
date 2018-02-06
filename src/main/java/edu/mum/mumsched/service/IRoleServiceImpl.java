package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Role;
import edu.mum.mumsched.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public List<Role> findAllExcept(String role1, String role2) {
        return roleRepository.findAllByRoleIsNotAndRoleIsNot(role1, role2);
    }
}
