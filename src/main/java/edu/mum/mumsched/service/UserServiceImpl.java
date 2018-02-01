package edu.mum.mumsched.service;


import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Role;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.model.UserForm;
import edu.mum.mumsched.repository.RoleRepository;
import edu.mum.mumsched.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends ConcreteServiceImpl<User, UserForm>{

}
