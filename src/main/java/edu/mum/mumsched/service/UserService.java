package edu.mum.mumsched.service;


import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.model.User;

import java.util.List;

public interface UserService {
	public User findUserByEmail(String email);
	public User findById(Integer id);
	public void saveUser(User user);
	public void updateUser(User user);
	public void delete(Integer id);
	public List<User> findAll();
	public User generateUserByStudent(Student student);
	public User generateUserByFaculty(Faculty faculty);
}
