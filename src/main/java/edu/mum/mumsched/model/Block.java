package edu.mum.mumsched.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collections;
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
    @OneToMany(mappedBy = "block")
    private Set<Section> sections;

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


}
