package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.Set;
@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private int id;
    private int facultyID;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String username;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_preferred_blocks",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id")})
    private Set<Block> preferredBlocks;
    @OneToMany(mappedBy = "faculty")
    private Set<Section> sections;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_courses",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> preferredCourses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Block> getPreferredBlocks() {
        return preferredBlocks;
    }

    public void setPreferredBlocks(Set<Block> preferredBlocks) {
        this.preferredBlocks = preferredBlocks;
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
