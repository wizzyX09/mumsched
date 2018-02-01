package edu.mum.mumsched.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class Block {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Specify block name")
    private String blockName;
    private Date startDate;
    private Date endDate;
    private int numberOfFppCourse;
    private int numberOfMppCourse;
    private int sequenceNumber;
    @OneToMany(mappedBy = "block",cascade = CascadeType.ALL)
    private Set<Section> sections;
    @OneToOne
    private Entry entry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfFppCourse() {
        return numberOfFppCourse;
    }

    public void setNumberOfFppCourse(int numberOfFppCourse) {
        this.numberOfFppCourse = numberOfFppCourse;
    }

    public int getNumberOfMppCourse() {
        return numberOfMppCourse;
    }

    public void setNumberOfMppCourse(int numberOfMppCourse) {
        this.numberOfMppCourse = numberOfMppCourse;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Set<Section> getSections() {
        return Collections.unmodifiableSet(sections);
    }

    public void addSection(Section section){
        if(section!=null) {
            sections.add(section);
            section.setBlock(this);
        }
    }

    public void removeSection(Section section){
        if(section!=null){
            sections.remove(section);
            section.setBlock(null);
        }
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public void createSections(List<Course> courseList, Entry entry) throws Exception {
        int seatsNeeded=entry.getFppNumber()+entry.getMppNumber();
        while (seatsNeeded>0){
            Course course=Course.bestCourse(courseList);
            if(course==null)
                throw new Exception("Not enough course available");
            Section section=new Section();
            section.setName(course.getName()+"-"+this.getBlockName());
            section.setCourse(course);
            section.setFaculty(course.getBestFaculty());
            section.setCapacity(30);
            course.getSections().add(section);
            seatsNeeded-=30;
            this.addSection(section);
        }
    }
}
