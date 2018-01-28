package edu.mum.mumsched.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Course implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String name;
    private String specialization;//shoud be an enum
    @OneToMany
    private Set<Course> prerequisites;

    @ManyToMany(mappedBy = "preferredCourses")
    private Set<Faculty> faculties;

    @OneToMany(mappedBy = "course")
    private Set<Section> sections;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }


    public Set<Faculty> getFaculties() {
        return faculties;
    }



    public Set<Section> getSections() {
        return sections;
    }




}
