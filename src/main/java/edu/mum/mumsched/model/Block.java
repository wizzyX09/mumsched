package edu.mum.mumsched.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

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
}
