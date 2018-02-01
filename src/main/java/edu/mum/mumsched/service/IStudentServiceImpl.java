package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.model.UserForm;
import edu.mum.mumsched.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class IStudentServiceImpl extends ConcreteServiceImpl<Student, UserForm> {

}
