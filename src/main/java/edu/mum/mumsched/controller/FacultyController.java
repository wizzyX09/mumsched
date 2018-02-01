package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.UserForm;
import edu.mum.mumsched.service.IFacultyService;
import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FacultyController {
    @Autowired
    UserService<Faculty, UserForm> facultyService;

}
