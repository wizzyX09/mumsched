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
    @Column(unique = true)
    private String email;
    //faculty account
    @OneToOne
    private User user;
    //faculty specialization track
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    //faculty sections

    /* @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_unwanted_blocks",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id")})
    */

    @ElementCollection(targetClass = BlockMonths.class)
    @JoinTable(name = "faculty_unwanted_blocks", joinColumns = @JoinColumn(name = "faculty_id"))
    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<BlockMonths> unwantedBlocks;

    @OneToMany(mappedBy = "faculty")
    private Set<Section> sections;
    //faculty course preferences
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "faculty_courses",
            joinColumns = {@JoinColumn(name = "faculty_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> preferredCourses;

    @ElementCollection(targetClass = BlockMonths.class)
    @JoinTable(name = "faculty_unwanted_blocks", joinColumns = @JoinColumn(name = "faculty_id"))
    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<BlockMonths> unwantedBlocks;
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

    public Set<BlockMonths> getUnwantedBlocks() {
        return unwantedBlocks;
    }

    public void setUnwantedBlocks(Set<BlockMonths> unwantedBlocks) {
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Set<BlockMonths> getUnwantedBlocks() {
        return unwantedBlocks;
    }

    public void setUnwantedBlocks(Set<BlockMonths> unwantedBlocks) {
        this.unwantedBlocks = unwantedBlocks;
    }
    
    public boolean isAvailable(Block block) {
        List<BlockMonths> blocks = this.getUnwantedBlocks().stream().filter(b1->b1.toString().contains(block.getBlockName())).collect(Collectors.toList());
        return blocks.size() == 0 ? true : false;
    }

    public void addSection(Section section){
        if(section!=null){
            section.setFaculty(this);
            this.sections.add(section);
        }
    }

}
