package edu.mum.mumsched.model;

import edu.mum.mumsched.exception.CourseNotFoundException;
import edu.mum.mumsched.util.COURSE;
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

    public void createSections(List<Course> courseList, Entry entry)throws RuntimeException{
        //Now compute how many more seats are needed
        int seatsNeeded = entry.getFppNumber() + entry.getMppNumber();
           //than check how many FPP sections are needed
        if (numberOfFppCourse > 0) {
            Course fpp = findFPPorMPP(COURSE.FPP,courseList);
            int temp = this.numberOfFppCourse;
            while (temp > 0) {
                Section sect = addSection(fpp, temp);
                entry.getSchedule().addSection(sect);
                seatsNeeded -= sect.getCapacity();
                temp--;
            }

        }//end of ffp section creation

        //than check how many MPP sections are needed
        if (numberOfMppCourse > 0) {
            Course mpp = findFPPorMPP(COURSE.MPP,courseList);
            int temp = this.numberOfMppCourse;
            while (temp > 0) {
                Section sect = addSection(mpp, temp);
                sect.setSchedule(entry.getSchedule());
                entry.getSchedule().addSection(sect);
                seatsNeeded -= sect.getCapacity();
                temp--;
            }
        }//end of mpp section creation

        if (seatsNeeded <= 0)
            return;
        //if the block as previous section , compute available seats
        int seatsAlreadyAvailable = 0;
        for (Section temp : this.getSections()) {
            if (!temp.getName().contains(COURSE.MPP.toString()) && !temp.getName().contains(COURSE.FPP.toString()))
                seatsAlreadyAvailable += temp.getAvailableSeats();
        }
        //create extra section
        seatsNeeded -= seatsAlreadyAvailable;
        int temp = 0;
        while (seatsNeeded > 0) {
            Course course = Course.getBestCourse(courseList);
            Section sect = addSection(course, temp);
            sect.setSchedule(entry.getSchedule());
            entry.getSchedule().addSection(sect);
            seatsNeeded -= sect.getCapacity();
            temp++;
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
 private Course  findFPPorMPP(COURSE desc,List<Course> courses)throws RuntimeException{
 /*    System.out.println("=====================Courses List======================================");
     for(Course c:courses){
         System.out.println(c.getName());
     }*/
      Course course = courses.stream().filter(c -> c.getName().equalsIgnoreCase(desc.toString())).findFirst().get();
      if(course==null){
          throw new CourseNotFoundException("This block require FPP or MPP section, block name: " + this.getBlockName() + ", But No such course is found in database");
      }
      //courses.remove(course);
      return course;
  }

    public void setNumberOfMppCourse(Integer numberOfMppCourse) {
        this.numberOfMppCourse = numberOfMppCourse;
    }
}
