package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IFacultyServiceImp implements IFacultyService {
    @Autowired
    FacultyRepository facultyRepository;


    @Override
    public Faculty findById(Integer facultyId) {
        return facultyRepository.findOne(facultyId);
    }

    @Override
    public void delete(Integer facultyId) {
        facultyRepository.delete(facultyId);
    }

    @Override
    public void save(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }
}