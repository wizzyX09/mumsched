package edu.mum.mumsched.controller;

import edu.mum.mumsched.SectionRegistrationSubsystem.ISectionRegistrationSubsystem;
import edu.mum.mumsched.model.Course;
import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.model.Specialization;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IFacultyService;
import edu.mum.mumsched.service.IStudentService;
import edu.mum.mumsched.util.SpecializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IFacultyService iFacultyService;

    @Autowired
    private ISectionRegistrationSubsystem sectionRegistrationFacade;

    @Autowired
    private IStudentService iStudentService;

    @GetMapping("/allCourse")
    public String findAll(Model model){
        model.addAttribute("allCourse", iCourseService.findAll());
        return "course/manage";
    }

    @GetMapping("/newCourse")
    public String addCourseForm(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("prerequisiteList", iCourseService.findAll());
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("specializationList", SpecializationUtil.getSpecializations());
        return "course/form";
    }

    @GetMapping("/updateCourse/{id}")
    public String updateCourseForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("course", iCourseService.findById(id));
        model.addAttribute("prerequisiteList", iCourseService.findAllExcept(id));
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("specializationList", SpecializationUtil.getSpecializations());
        return "course/form";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute @Valid Course course, Model model,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        bindingResult.hasErrors();
        List<Course> duplicates = iCourseService.findDuplicates(course.getName(), course.getId());
        if (duplicates.size() > 0) {
            redirectAttributes.addFlashAttribute("messageError", "The course name cannot be duplicated.");
            if (course.getId() > 0) {
                return "redirect:/updateCourse/" + course.getId();
            } else {
                return "redirect:/newCourse";
            }
        }
        iCourseService.save(course);
        return "redirect:/allCourse";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourseForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        Course course = iCourseService.findById(id);
        List<Course> prereqs = iCourseService.findAllByPrerequisitesContains(course);
        List<Faculty> faculties = iFacultyService.findAllByPreferredCoursesContains(course);
        List<Section> sections = sectionRegistrationFacade.findByCourse(course);
        if(prereqs.size() > 0 || faculties.size() > 0 || sections.size() > 0) {
            String messages = "The course was not deleted!";
            if(prereqs.size() > 0) {
                messages += " Course is used for prerequisite.";
            }
            if(faculties.size() > 0) {
                messages += " Course is used for Faculty's preferred course.";
            }
            if(faculties.size() > 0) {
                messages += " Course is used for Faculty's preferred course.";
            }
            if(sections.size() > 0) {
                messages += " Course is used for Section.";
            }
            redirectAttributes.addFlashAttribute("messageError", messages);
        } else {
            iCourseService.delete(id);
        }
        return "redirect:/allCourse";
    }

    @GetMapping("/detailsCourse/{id}")
    public String detailsCourseForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("course", iCourseService.findById(id));
        return "course/details";
    }

}
