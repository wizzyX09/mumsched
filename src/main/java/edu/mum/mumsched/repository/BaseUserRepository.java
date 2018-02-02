package edu.mum.mumsched.repository;

import edu.mum.mumsched.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// @NoRepositoryBean
public interface BaseUserRepository<TEntity extends User> extends JpaRepository<TEntity, Integer> {
    TEntity findByEmail(String email);
    // TEntity findByUsername(String username);
}
