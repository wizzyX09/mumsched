package edu.mum.mumsched.model;

import org.hibernate.mapping.Map;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Specify block name")
    private String blockName;
    private Date startDate;
    private Date endDate;
    private int numberOfFppCourse;
    private int numberOfMppCourse;
    private int sequenceNumber;
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
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
        return sections;
    }

    public void addSection(Section section) {
        if (section != null) {
            sections.add(section);
            section.setBlock(this);
        }
    }

    public void removeSection(Section section) {
        if (section != null) {
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
       //if the block as previous section, compute available seats
        int seatsAlreadyAvailable = 0;
        for (Section temp : this.getSections()) {
            seatsAlreadyAvailable+=temp.getAvailableSeats();
        }
        //Now compute how many more seats are needed
        int seatsNeeded = entry.getFppNumber() + entry.getMppNumber()-seatsAlreadyAvailable;
        //than check how many MPP or FPP sections are needed
        if(numberOfFppCourse>0){
            Course fpp=courseList.stream().filter(c->c.getName().contains("FPP")).findFirst().get();
           if(fpp==null) {
               throw new Exception("This block require FPP section, block name: "+this.getBlockName()+", But No FPP course in database");
           }
           int temp=this.numberOfFppCourse;
           while (temp>0){
               Section sect=new Section ();
               sect.setName(fpp.getName()+"-"+this.getBlockName()+"-"+temp);
               sect.setCapacity(fpp.getInitialCapacity());
               sect.setEnrolled(0);
               sect.setAvailableSeats(fpp.getInitialCapacity());
               fpp.addSection(sect);
               this.addSection(sect);
               Faculty fact=fpp.getBestFaculty(this);
               if(fact==null)
                   sect.setFaculty(fact);



           }

        }


        while (seatsNeeded > 0) {
            Course course = Course.getBestCourse(courseList);
            if (course == null)
                throw new Exception("Not enough course available");
            Section section = new Section();
            section.setName(course.getName() + "-" + this.getBlockName());
            section.setCourse(course);
            section.setFaculty(course.getBestFaculty(this));
            section.setCapacity(30);
            course.getSections().add(section);
            seatsNeeded -= 30;
            this.addSection(section);
        }
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}
