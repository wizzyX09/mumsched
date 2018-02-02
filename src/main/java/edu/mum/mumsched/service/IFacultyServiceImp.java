package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.UserForm;
import org.springframework.stereotype.Service;

@Service("facultyService")
public class IFacultyServiceImp extends ConcreteServiceImpl<Faculty, UserForm> {

}
