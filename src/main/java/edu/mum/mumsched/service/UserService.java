package edu.mum.mumsched.service;


import edu.mum.mumsched.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
