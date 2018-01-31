package edu.mum.mumsched.controller;

import edu.mum.mumsched.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FacultyController {
    @Autowired
    IFacultyService iFacultyService;

}
