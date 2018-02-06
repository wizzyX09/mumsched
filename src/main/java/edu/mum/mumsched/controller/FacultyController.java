package edu.mum.mumsched.controller;

import edu.mum.mumsched.SectionRegistrationSubsystem.ISectionRegistrationSubsystem;
import edu.mum.mumsched.model.*;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IEntryService;
import edu.mum.mumsched.service.IFacultyService;
import edu.mum.mumsched.util.MonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    IEntryService iEntryService;

    @Autowired
    ISectionRegistrationSubsystem sectionRegistrationFacade;

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

    @GetMapping("/section")
    public String displaySections(Model model) {
        // to display list of sections in drop down list
        List<Section> sectionList = sectionRegistrationFacade.sectionList();
        model.addAttribute("sectionList", sectionList);

        // created as a container to hold multiple sections from selection
        model.addAttribute("faculty", new Faculty());

        // to display a list of sections preferred by faculty in a table
        Faculty faculty = getLoggedInFaculty();
        Set<Section> sections = faculty.getSections();
        model.addAttribute("selectedSections", sections);

        return "faculty/section/manage";
    }

    @PostMapping("/section/add")
    public String addSections(@ModelAttribute("faculty") Faculty facultyContainer) {
        Faculty connectedFaculty = getLoggedInFaculty();
        Set<Section> selectedSectionList = facultyContainer.getSections();
        if (facultyContainer != null) {
            connectedFaculty.getSections().addAll(selectedSectionList);
            iFacultyService.save(connectedFaculty);
        }
        return "redirect:/faculty/section";
    }

    @GetMapping("/faculty/section/delete/{id}")
    public String deleteSections(@PathVariable("id") Integer id) {
        Faculty faculty = getLoggedInFaculty();
        Set<Section> sections = faculty.getSections();

        Iterator<Section> it = sections.iterator();
        while (it.hasNext()) {
            Section section = it.next();
            if (section.getId() == id) {
                it.remove();
            }
        }
        iFacultyService.save(faculty);
        return "redirect:/faculty/section";
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
        Faculty faculty = getLoggedInFaculty();
        model.addAttribute("faculty", faculty);
        return "faculty/profile/manage";
    }

    @GetMapping("/profile-update")
    public String updateFacultyProfile(Model model) {
        Faculty faculty = getLoggedInFaculty();
        model.addAttribute("faculty", faculty);
        return "faculty/profile/update";
    }

    @PostMapping("/profile/update")
    public String saveFacultyProfile(@ModelAttribute("faculty") Faculty faculty){
        iFacultyService.save(faculty);
        return "redirect:/faculty/profile";
    }

    @GetMapping("/block")
    public String displayUnwantedBlocks(Model model) {
        List<BlockMonths> monthBlockList = MonthUtil.getMonths();
        model.addAttribute("monthBlockList", monthBlockList);
        model.addAttribute("faculty",new Faculty());

        // to dispaly list of courses preferred by faculty in a table
        Faculty faculty = getLoggedInFaculty();
        Set<BlockMonths> unwantedBlocks = faculty.getUnwantedBlocks();
        model.addAttribute("unwantedBlocks", unwantedBlocks);
        return "faculty/block/manage";
    }

    @PostMapping("/block/add")
    public String addUnwantedBlocks(@ModelAttribute("faculty") Faculty faculty) {
        Faculty connectedFaculty = getLoggedInFaculty();
        List<BlockMonths> monthBlockList = MonthUtil.getMonths();
        if (faculty != null) {
            connectedFaculty.getUnwantedBlocks().addAll(faculty.getUnwantedBlocks());
            iFacultyService.save(connectedFaculty);
        }

        return "redirect:/faculty/block";
    }

    @GetMapping("/block/delete/{unwantedBlock}")
    public String deleteUnwantedBlocks(@PathVariable("unwantedBlock") BlockMonths unwantedBlock) {
        Faculty faculty = getLoggedInFaculty();
        Set<BlockMonths> blockMonths = faculty.getUnwantedBlocks();

        Iterator<BlockMonths> it = blockMonths.iterator();
        while (it.hasNext()) {
            BlockMonths block = it.next();
            if (block.equals(unwantedBlock)) {
                it.remove();
            }
        }
        iFacultyService.save(faculty);
        return "redirect:/faculty/block";
    }

    @GetMapping("/schedule")
    public String viewSchedule(Model model) {
        model.addAttribute("entries", iEntryService.findAll());
        return "faculty/schedule/view";
    }

    public Faculty getLoggedInFaculty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Faculty faculty = iFacultyService.findByEmail(email);
        return faculty;
    }
}
