package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="student")
public class Student extends User {
    private Integer studentId;
<<<<<<< HEAD
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
=======

>>>>>>> 971d3ff... user changes related to inheritance
    @ManyToMany(mappedBy = "students")
    private Set<Section> sections;

    @OneToOne
    private Entry entry;
    @Embedded
    private List<Track> tracks;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
<<<<<<< HEAD

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

    public Set<Section> getSections() {
        return sections;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
=======
>>>>>>> 971d3ff... user changes related to inheritance
}
