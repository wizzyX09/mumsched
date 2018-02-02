package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="faculty")
public class Faculty extends User {
    private int facultyID;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_unwanted_blocks",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id")})
    private Set<Block> unwantedBlocks;

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

    public Set<Block> getUnwantedBlocks() {
        return unwantedBlocks;
    }

    public void setUnwantedBlocks(Set<Block> unwantedBlocks) {
        this.unwantedBlocks = unwantedBlocks;

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
    //it is not correct yet
    public boolean isAvailable(Block block) {
        List<Block> blocks = this.getUnwantedBlocks().stream().filter(bl -> bl.getBlockName().contains(block.getBlockName())).collect(Collectors.toList());
        return blocks.size() == 0 ? true : false;
    }

    public void addSection(Section section){
        if(section!=null){
            section.setFaculty(this);
            this.sections.add(section);
        }
    }

}
