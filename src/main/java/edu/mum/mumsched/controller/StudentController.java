package edu.mum.mumsched.controller;

import edu.mum.mumsched.SectionRegistrationSubsystem.ISectionRegistrationSubsystem;
import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.model.Student;
import edu.mum.mumsched.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    IStudentService iStudentService;
    @Autowired
    ISectionRegistrationSubsystem sectionRegistrationFacade;

    @GetMapping("/register")
    public String registerSectionForm(Model model, Principal principal){
        if(principal == null)
            return "redirect:/login";

        String studentEmail = principal.getName();
        Student student = iStudentService.findByEmail(studentEmail);//student object

        Entry entry = student.getEntry();
        entry.getBlocks();
        System.out.println("entry **********************************  ************  " +  entry.getEntryName() + " - " + entry.getMppNumber());

        List<Section> sectionList = sectionRegistrationFacade.findAll();
        Set<Section> studentSections = student.getSections();

        for (Section sSec:studentSections) {
            if(sectionList.contains(sSec))
                sectionList.remove(sSec);
        }

        //  sectionList.stream().filter(s->s.equals())

        model.addAttribute("sectionRegistration", student);//student object
        model.addAttribute("sectionList", sectionList);//available sections
        model.addAttribute("studentSections", studentSections);//sections already registered for

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


        Section sec = student.getSections().iterator().next();
        System.out.println(sec.getStudents().size() +"  size**************************capacity     "+ sec.getCapacity());
        if(sec.getStudents().size() >= sec.getCapacity()){
            redirectAttributes.addFlashAttribute("studentSections", studentToPersist.getSections());
            redirectAttributes.addFlashAttribute("errorMes", "Section reach capacity choose another!");
            return "redirect:/student/register";
        }


        studentToPersist.addSection(sec);
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

        student.removeSection(sectionRegistrationFacade.findById(id));
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
