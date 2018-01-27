package edu.mum.mumsched.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="course")
public class Section {
    @Id@GeneratedValue
    private Integer id;
    private String name;
    private Integer capacity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "section_student",
            joinColumns = { @JoinColumn(name = "section_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") })
    private Set<Student> students;

//    @OneToOne(mappedBy = "sections")
//    private Block block;
//
//    @OneToOne(mappedBy = "sections")
//    private Faculty faculty;
//
//    @OneToOne(mappedBy = "sections")
//    private Course course;

    public Section() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
