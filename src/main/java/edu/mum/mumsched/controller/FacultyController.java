package edu.mum.mumsched.controller;

import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @GetMapping("/course")
    public String displayPreferredCourses(Model model) {
        return "faculty/course/manage";
    }

    @GetMapping("/profile")
    public String displayFacultyProfile(Model model) {
        return "faculty/profile/manage";
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
