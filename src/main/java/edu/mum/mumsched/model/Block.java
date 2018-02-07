package edu.mum.mumsched.model;

import edu.mum.mumsched.exception.CourseNotFoundException;
import edu.mum.mumsched.util.COURSE;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Specify block name")
    private String blockName;//must be unique
    private Date startDate;
    private Date endDate;
    private Integer numberOfFppCourse;
    private Integer numberOfMppCourse;
    private Integer sequenceNumber;
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
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

    public void setNumberOfFppCourse(Integer numberOfFppCourse) {
        this.numberOfFppCourse = numberOfFppCourse;
    }

    public Integer getNumberOfMppCourse() {
        return numberOfMppCourse;
    }

    public void setNumberOfMppCourse(int numberOfMppCourse) {
        this.numberOfMppCourse = numberOfMppCourse;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
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

    public void createSections(Course fpp,Course mpp,List<Course> courseList, Entry entry)throws RuntimeException{

        //if the block as previous section , compute available seats
        int seatsAlreadyAvailable = 0;
        for (Section temp : this.getSections()) {
            if (!temp.getName().equalsIgnoreCase("MPP") && !temp.getName().equalsIgnoreCase("FPP"))
                seatsAlreadyAvailable += temp.getAvailableSeats();
        }
        //Now compute how many more seats are needed
        int seatsNeeded = entry.getFppNumber() + entry.getMppNumber();
           //than check how many FPP sections are needed
        if (numberOfFppCourse > 0) {
            int temp = this.numberOfFppCourse;
            while (temp > 0) {
                Section sect = createSection(fpp, temp);
                entry.getSchedule().addSection(sect);
                seatsNeeded -= sect.getCapacity();
                temp--;
            }

        }//end of ffp section creation

        //than check how many MPP sections are needed
        if (numberOfMppCourse > 0) {
            int temp = this.numberOfMppCourse;
            while (temp > 0) {
                Section sect = createSection(mpp, temp);
                entry.getSchedule().addSection(sect);
                temp--;
                seatsNeeded -= sect.getCapacity();
            }
        }//end of mpp section creation

        if (seatsNeeded <= 0)
            return;

        //create extra section
        seatsNeeded -= seatsAlreadyAvailable;
        int temp = 0;
        while (seatsNeeded > 0) {
            Course course = Course.getBestCourse(courseList);
            Section sect = createSection(course, temp);
            entry.getSchedule().addSection(sect);
            seatsNeeded -= sect.getCapacity();
            temp++;
        }//done created extra course
    }

    public Section createSection(Course course, int counter) {
        System.out.println("Block:"+this.getBlockName()+"Course:"+course.getName());
        Section sect = new Section();
        sect.setName(course.getName() + "-" + this.getBlockName() + "-" + counter);
        sect.setCapacity(course.getInitialCapacity());
        sect.setEnrolled(0);
        sect.setAvailableSeats(course.getInitialCapacity());
        course.addSection(sect);
        this.addSection(sect);
        Faculty fact = course.getBestFaculty(this);
        if (fact != null)
            fact.addSection(sect);
        return sect;
    }


    public void setNumberOfMppCourse(Integer numberOfMppCourse) {
        this.numberOfMppCourse = numberOfMppCourse;
    }
}
