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
import java.util.List;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    ICourseService iCourseService;

    @Autowired
    IFacultyService iFacultyService;

    @GetMapping("/course")
    public String displayPreferredCourses(Model model) {
        List<Course> courseList = iCourseService.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("preferredCourse", new Course());
        return "faculty/course/manage";
    }

    @PostMapping("/course/add")
    public String addPreferredCourse(@Valid @ModelAttribute("preferredCourse") Course preferredCourse, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Faculty faculty = iFacultyService.findByEmail(email);
        Course course = iCourseService.findById(preferredCourse.getId());
        if (faculty != null && course != null) {
            faculty.getPreferredCourses().add(course);
            iFacultyService.save(faculty);
        }
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
}
