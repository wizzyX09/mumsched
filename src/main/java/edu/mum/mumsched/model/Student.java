package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer studentId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String email;
    private String username;
    @ManyToMany(mappedBy = "students", cascade=CascadeType.ALL)
    //enrolled sections
    private Set<Section> sections;
    //student entry
    @OneToOne
    private Entry entry;
    //student study track
    @Enumerated(EnumType.STRING)
    private StudyTrack studyTrack;
    //student work track
    @Enumerated(EnumType.STRING)
    private WorkTrack workTrack;
    //student account
    @OneToOne
    private User user;

    public void addSection(Section sec){
        sections.add(sec);
        sec.addStudent(this);
    }

    public void removeSection(Section sec){
        sections.remove(sec);
        sec.removeStudent(this);
        /*for (Section s : sections) {
            if (s.getId() == secId) {

                return;
            }
        }*/
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
    public StudyTrack getStudyTrack() {
        return studyTrack;
    }

    public void setStudyTrack(StudyTrack studyTrack) {
        this.studyTrack = studyTrack;
    }

    public WorkTrack getWorkTrack() {
        return workTrack;
    }

    public void setWorkTrack(WorkTrack workTrack) {
        this.workTrack = workTrack;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

}
