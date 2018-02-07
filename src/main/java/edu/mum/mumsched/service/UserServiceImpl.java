package edu.mum.mumsched.service;


import ch.qos.logback.core.net.SyslogOutputStream;
import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.repository.FacultyRepository;
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
    private FacultyRepository facultyRepository;
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
	public void updateUser(User user) {
		if(user.getId() != 0) {
			User userDB = userRepository.findOne(user.getId());
			userDB.setFirstName(user.getFirstName());
			userDB.setLastName(user.getLastName());
			userDB.setEmail(user.getEmail());
			userDB.setActive(user.getActive());
			userRepository.save(userDB);
		} else {
			saveUser(user);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer id) {
		User user = userRepository.findOne(id);
		if(!studentRepository.findAllByUserIs(user).isEmpty()) {
			Student student = studentRepository.findByUser(user);
			student.setUser(null);
			studentRepository.save(student);
		}
		if(!facultyRepository.findAllByUserIs(user).isEmpty()) {
			Faculty faculty = facultyRepository.findByUser(user);
			faculty.setUser(null);
			facultyRepository.save(faculty);
		}
		userRepository.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
    public User findById(Integer id) {
        return userRepository.findOne(id);
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
		System.out.println("password " + password);
//		iEmailService.sendGeneratedAccountMail(user.getEmail(), password);
		return user;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User generateUserByFaculty(Faculty faculty) {
        String password = RandomUtil.generateRandomPassword();
        User user = new User();
        user.setFirstName(faculty.getFirstName());
        user.setLastName(faculty.getLastName());
        user.setEmail(faculty.getEmail());
        user.setRoles(roleRepository.findAllByRole(("FACULTY")));
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setActive(true);
        userRepository.save(user);
        faculty.setUser(user);
        facultyRepository.save(faculty);
        iEmailService.sendGeneratedAccountMail(user.getEmail(), password);
        return user;
    }


}
