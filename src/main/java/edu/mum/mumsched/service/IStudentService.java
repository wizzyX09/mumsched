package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Student;

import java.util.List;

public interface IStudentService {
    public Student findById(Integer studentId);
    public void delete(Integer studentId);
    public void save(Student student);
    public List<Student> findAll();
}
