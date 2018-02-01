package edu.mum.mumsched.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="student")
public class Student extends User {
    private Integer studentId;

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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

}
