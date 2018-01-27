package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IStudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student findById(Integer studentId) { return studentRepository.findOne(studentId); }

    @Override
    public void delete(Integer studentId) {
        studentRepository.delete(studentId);
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
