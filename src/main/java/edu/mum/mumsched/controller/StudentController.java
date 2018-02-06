package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.service.ISectionService;
import edu.mum.mumsched.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    IStudentService iStudentService;
    @Autowired
    ISectionService iSectionService;

    @GetMapping("/register")
    public String registerSectionForm(Model model, Principal principal){
        if(principal == null)
            return "redirect:/login";

        String studentEmail = principal.getName();
        Student student = iStudentService.findByEmail(studentEmail);
        model.addAttribute("sectionRegistration", student);
        model.addAttribute("sectionList", iSectionService.findAll());
        model.addAttribute("studentSections", student.getSections());

       // studentSections

        return "section/register";
    }

    @PostMapping("/saveSecRegistration")
    public String saveRegistration(@ModelAttribute("sectionRegistration") Student student,
                            Model model, RedirectAttributes redirectAttributes, Principal principal){

        if(principal == null)
            return "redirect:/login";
        String studentEmail = principal.getName();

        Student studentToPersist = iStudentService.findByEmail(studentEmail);
        studentToPersist.addSection(student.getSections().iterator().next());
        iStudentService.save(studentToPersist);

        for (Section s: studentToPersist.getSections()) {
            System.out.println("\n\n\nFirst*****************"+ s.getName() + "**********************************************************\n\n");
        }


        redirectAttributes.addFlashAttribute("studentSections", studentToPersist.getSections());
        return "redirect:/student/register";
    }

    @GetMapping("/deleteSection/{id}")
    public String deleteSection(@PathVariable("id")Integer id, Principal principal,RedirectAttributes redirectAttributes){

        if(principal == null)
            return "redirect:/login";

        String studentEmail = principal.getName();
        Student student = iStudentService.findByEmail(studentEmail);

        student.removeSection(iSectionService.findById(id));
        iStudentService.save(student);

        return "redirect:/student/register";
        /*
         public String deleteSection(@PathVariable("id")Integer id, RedirectAttributes redirectAttributes){
        iSectionService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Section successfully delete!");
        return "redirect:/section/manage";
    }
         */
    }
}
