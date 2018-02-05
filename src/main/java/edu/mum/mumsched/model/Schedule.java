package edu.mum.mumsched.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule implements Serializable{
    @Id @GeneratedValue
    private Integer id;
    private Date generatedDate;
    private Date approvedDate;
    private String status;//should be an enum

    @OneToOne(mappedBy = "schedule",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Entry entry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    public Schedule generate(List<Course> courseList) throws Exception {
        for(Block block:this.getEntry().getBlocks()){
            block.createSections(courseList,this.getEntry());
        }
        this.getEntry().setSchedule(this);
        this.setGeneratedDate(new Date(System.currentTimeMillis()));
        this.setStatus("DRAFT");
        return this;
    }

}
