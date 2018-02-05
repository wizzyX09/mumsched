package edu.mum.mumsched.service;


import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.repository.RoleRepository;
import edu.mum.mumsched.repository.StudentRepository;
import edu.mum.mumsched.repository.UserRepository;
import edu.mum.mumsched.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	private StudentRepository studentRepository;
    @Autowired
	private IEmailService iEmailService;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
		userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User generateUserByStudent(Student student) {
		String password = RandomUtil.generateRandomPassword();
		User user = new User();
		user.setFirstName(student.getFirstName());
		user.setLastName(student.getLastName());
		user.setEmail(student.getEmail());
		user.setRoles(roleRepository.findAllByRole(("STUDENT")));
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setActive(true);
		userRepository.save(user);
		student.setUser(user);
		studentRepository.save(student);
		iEmailService.sendGeneratedAccountMail(user.getEmail(), password);
		return user;
	}



}
