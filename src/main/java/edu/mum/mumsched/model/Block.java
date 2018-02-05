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
    private String blockName;//must be unique
    private Date startDate;
    private Date endDate;
    private int numberOfFppCourse;
    private int numberOfMppCourse;
    private int sequenceNumber;
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

    public void createSections(List<Course> courseList, Entry entry) throws Exception {
        //Now compute how many more seats are needed
        int seatsNeeded = entry.getFppNumber() + entry.getMppNumber();
        if (seatsNeeded <= 0)
            throw new Exception("The MPP and FPP projection is incorrect, edit the entry details to proceed");
        //than check how many FPP sections are needed
        if (numberOfFppCourse > 0) {
            Course fpp = courseList.stream().filter(c -> c.getName().contains("FPP")).findFirst().get();
            if (fpp == null) {
                throw new Exception("This block require FPP section, block name: " + this.getBlockName() + ", But No FPP course in database");
            }
            int temp = this.numberOfFppCourse;
            while (temp > 0) {
                Section sect = addSection(fpp, temp);
                seatsNeeded -= sect.getCapacity();
                temp--;
            }

        }//end of ffp section creation

        //than check how many MPP sections are needed
        if (numberOfMppCourse > 0) {
            Course mpp = courseList.stream().filter(c -> c.getName().contains("MPP")).findFirst().get();
            if (mpp == null) {
                throw new Exception("This block require MPP section, block name: " + this.getBlockName() + ", But No MPP course in database");
            }
            int temp = this.numberOfMppCourse;
            while (temp > 0) {
                Section sect = addSection(mpp, temp);
                temp--;
                seatsNeeded -= sect.getCapacity();
            }

        }//end of mpp section creation

        if (seatsNeeded <= 0)
            return;
        //if the block as previous section , compute available seats
        int seatsAlreadyAvailable = 0;
        for (Section temp : this.getSections()) {
            if (!temp.getName().contains("MPP") && !temp.getName().contains("FPP"))
                seatsAlreadyAvailable += temp.getAvailableSeats();
        }
        //create extra section
        seatsNeeded -= seatsAlreadyAvailable;
        int temp = 0;
        while (seatsNeeded > 0) {
            Course course = Course.getBestCourse(courseList);
            if (course == null)
                throw new Exception("Not enough course available");
            Section sect = addSection(course, temp);
            temp++;
            seatsNeeded -= sect.getCapacity();
        }//done created extra course
    }

    public Section addSection(Course course, int counter) {
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
}
