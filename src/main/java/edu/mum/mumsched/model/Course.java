package edu.mum.mumsched.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String name;
    private int initialCapacity;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @ManyToMany
    private Set<Course> prerequisites;
    @ManyToMany(mappedBy = "preferredCourses")
    private Set<Faculty> faculties;
    @OneToMany(mappedBy = "course")
    private Set<Section> sections;

    public static Course getBestCourse(List<Course> courseList) {
        return courseList.size() > 0 ? courseList.remove(0) : null;
    }

    public static List<Course> orderCourses(List<Course> courseList) {
        //first let's put all prerequisites to the front
        List<Course> sortedList = courseList.stream().filter(course -> course.getPrerequisites().size() == 0).collect(Collectors.toList());
        courseList.removeAll(sortedList);
        Collections.shuffle(sortedList);
        Collections.shuffle(courseList);
        sortedList.addAll(courseList);
        sortedList.forEach(course->System.out.println(course.getName()));
        return sortedList;
    }

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

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Set<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    //conveniences methods
    public void addPrerequisite(Course course) {
        if (course != null)
            prerequisites.add(course);
    }

    public boolean removePrerequisite(Course course) {
        if (course != null)
            return prerequisites.remove(course);
        return false;
    }

    public void addFaculty(Faculty faculty) {
        if (faculty != null)
            faculties.add(faculty);
    }

    public boolean removeFaculty(Faculty faculty) {
        if (faculty != null)
            return faculties.remove(faculty);
        return false;
    }

    public void addSection(Section section) {
        if (section != null) {
            sections.add(section);
            section.setCourse(this);
        }
    }

    public boolean removeSection(Section section) {
        if (section != null) {
            section.setCourse(null);
            return sections.remove(section);
        }
        return false;
    }

    public Faculty getBestFaculty(Block block) {
        if (this.getFaculties().isEmpty()) return null;
        Faculty bestFaculty = this.getFaculties().iterator().next();
        int sectionAlreadyTaught = bestFaculty.getSections().size();
        for (Faculty fac : this.getFaculties()) {
            int temp = fac.getSections().size();
            if (sectionAlreadyTaught > temp && fac.isAvailable(block) && !isLastToTeachThisCourse()) {
                bestFaculty = fac;
            }
        }

        return bestFaculty;
    }

    private boolean isLastToTeachThisCourse() {
        return false;
    }
}
