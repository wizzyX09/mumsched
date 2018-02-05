package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Block;
import edu.mum.mumsched.model.Course;
import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    ICourseService iCourseService;

    @Autowired
    IFacultyService iFacultyService;

    @GetMapping("/course")
    public String displayPreferredCourses(Model model) {
        // to display list of courses in drop down list
        List<Course> courseList = iCourseService.findAll();
        model.addAttribute("courseList", courseList);

        // when a course is selected from the drop down list, the id of the selected course
        // will be set to this course object
        model.addAttribute("course", new Course());

        // to dispaly list of courses preferred by faculty in a table
        Faculty faculty = getLoggedInFaculty();
        Set<Course> courses = faculty.getPreferredCourses();
        model.addAttribute("preferredCourses", courses);

        return "faculty/course/manage";

    }

    @PostMapping("/course/add")
    public String addPreferredCourse(@Valid @ModelAttribute("course") Course preferredCourse, BindingResult bindingResult) {
        Faculty faculty = getLoggedInFaculty();
        Course course = iCourseService.findById(preferredCourse.getId());
        if (faculty != null && course != null) {
            faculty.getPreferredCourses().add(course);
            iFacultyService.save(faculty);
        }
        return "redirect:/faculty/course";
    }

    @GetMapping("/course/delete/{id}")
    public String deletePreferredCourse(@PathVariable("id") Integer id) {
        Faculty faculty = getLoggedInFaculty();
        Set<Course> courses = faculty.getPreferredCourses();

        Iterator<Course> it = courses.iterator();
        while (it.hasNext()) {
            Course course = it.next();
            if (course.getId() == id) {
                it.remove();
            }
        }
        iFacultyService.save(faculty);
        return "redirect:/faculty/course";
    }

    @GetMapping("/profile")
    public String displayFacultyProfile(Model model) {
        return "faculty/profile/manage";
    }

    @GetMapping("/profile-update")
    public String updateFacultyProfile(Model model) {
        return "faculty/profile/update";
    }

    @GetMapping("/block")
    public String displayUnwantedBlocks(Model model) {
        return "faculty/block/manage";
    }

    @GetMapping("/schedule")
    public String viewSchedule(Model model) {
        return "faculty/schedule/view";
    }

    public Faculty getLoggedInFaculty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Faculty faculty = iFacultyService.findByEmail(email);
        return faculty;
    }
}
