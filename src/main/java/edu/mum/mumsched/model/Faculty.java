package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name="faculty")
public class Faculty extends User {
    private int facultyID;
<<<<<<< HEAD
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
=======

>>>>>>> 971d3ff... user changes related to inheritance
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_unwanted_blocks",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id")})
<<<<<<< HEAD
    private Set<Block> unwantedBlocks;
=======
    private Set<Block> preferredBlocks;

>>>>>>> 971d3ff... user changes related to inheritance
    @OneToMany(mappedBy = "faculty")
    private Set<Section> sections;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_courses",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> preferredCourses;

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

<<<<<<< HEAD
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Block> getUnwantedBlocks() {
        return unwantedBlocks;
    }

    public void setUnwantedBlocks(Set<Block> unwantedBlocks) {
        this.unwantedBlocks = unwantedBlocks;
=======
    public Set<Block> getPreferredBlocks() {
        return preferredBlocks;
    }

    public void setPreferredBlocks(Set<Block> preferredBlocks) {
        this.preferredBlocks = preferredBlocks;
>>>>>>> 971d3ff... user changes related to inheritance
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Set<Course> getPreferredCourses() {
        return preferredCourses;
    }

    public void setPreferredCourses(Set<Course> preferredCourses) {
        this.preferredCourses = preferredCourses;
    }
}
