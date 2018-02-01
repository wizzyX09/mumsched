package edu.mum.mumsched.service;


import edu.mum.mumsched.model.User;

import java.util.List;

public interface UserService<TEntity, TModel> {
	public TModel findOne(Integer id);
	public List<TModel> findAll();
	public void save(TModel model);
	public void delete(Integer id);
	public void update(TModel model);
	public TModel findByEmail(String email);
}
