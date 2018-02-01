package edu.mum.mumsched.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name="entries")
public class Entry {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "Specify an entry name")
    private String entryName;
    private Integer fppNumber;
    private Integer mppNumber;
    private Integer percentOpt;
    private Integer percentCpt;
    private Integer percentUsResident;
    private Date startDate;
    private Date endDate;
    @OneToOne
    private Schedule schedule;
    @OneToMany(mappedBy = "entry")
    private Set<Student> students;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "entry_blocks",
            joinColumns = {@JoinColumn(name = "entry_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id", referencedColumnName = "id")})
    private Set<Block> blocks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public Integer getFppNumber() {
        return fppNumber;
    }

    public void setFppNumber(Integer fppNumber) {
        this.fppNumber = fppNumber;
    }

    public Integer getMppNumber() {
        return mppNumber;
    }

    public void setMppNumber(Integer mppNumber) {
        this.mppNumber = mppNumber;
    }

    public Integer getPercentOpt() {
        return percentOpt;
    }

    public void setPercentOpt(Integer percentOpt) {
        this.percentOpt = percentOpt;
    }

    public Integer getPercentCpt() {
        return percentCpt;
    }

    public void setPercentCpt(Integer percentCpt) {
        this.percentCpt = percentCpt;
    }

    public Integer getPercentUsResident() {
        return percentUsResident;
    }

    public void setPercentUsResident(Integer percentUsResident) {
        this.percentUsResident = percentUsResident;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Student> getStudents() {
        return students;
    }
    public Set<Block> getBlocks() {
        return blocks;
    }
    //conveniences methods
    public void addStudent(Student student){
        if(student!=null) {
            students.add(student);
            student.setEntry(this);
        }
    }
    public boolean removeStudent(Student student){
        if(student!=null){
            student.setEntry(null);
          return  students.remove(student);
        }
        return false;
    }

    public void addBlock(Block block){
        if(block!=null) {
            blocks.add(block);
        }
    }
    public boolean removeBlock(Block block){
        if(block!=null){
            return  blocks.remove(block);
        }
        return false;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }
}
