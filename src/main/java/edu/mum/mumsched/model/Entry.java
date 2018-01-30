package edu.mum.mumsched.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name="entries")
public class Entry {
    @Id@GeneratedValue
    private Integer id;
    @NotNull(message = "Specify an entry name")
    private String entryName;
    private Integer fppNumber;
    private Integer mppNumber;
    private Date startDate;
    private Date endDate;
    @OneToOne
    private Schedule schedule;
    @OneToMany(mappedBy = "entry")
    private Set<Student> students;
    @OneToMany(mappedBy = "entry")
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
}
