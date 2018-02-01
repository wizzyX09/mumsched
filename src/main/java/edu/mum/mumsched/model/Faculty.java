package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name="faculty")
public class Faculty extends User {
    private int facultyID;

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

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
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
