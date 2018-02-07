package edu.mum.mumsched.controller;

import edu.mum.mumsched.SectionRegistrationSubsystem.ISectionRegistrationSubsystem;
import edu.mum.mumsched.model.Block;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        System.out.println("entry **********************************  ************  " +  entry.getEntryName() + " - " + entry.getMppNumber());

        //List<Section> sectionList = sectionRegistrationFacade.findAll();
        //Set<Section> studentSections = student.getSections();

        /*for (Section sSec:studentSections) {
            if(sectionList.contains(sSec))
                sectionList.remove(sSec);
        }*/

        //  sectionList.stream().filter(s->s.equals())

        //model.addAttribute("sectionRegistration", student);//student object
       // model.addAttribute("sectionList", sectionList);//available sections
        model.addAttribute("blocks", entry.getBlocks());//available sections
       // model.addAttribute("studentSections", studentSections);//sections already registered for

       // studentSections

        return "section/register";
    }


    @PostMapping("/registerSection")
    public String registerSectionFormSubmit(@RequestParam("blocks") Block b, Model model, Principal principal){
        if(principal == null)
            return "redirect:/login";

        String studentEmail = principal.getName();
        Student student = iStudentService.findByEmail(studentEmail);//student object

        Entry entry = student.getEntry();

        System.out.println("entry **********************************  ************  " +  entry.getEntryName() + " - " + entry.getMppNumber());

        List<Section> sectionList = sectionRegistrationFacade.findAll().stream().filter(s->s.getBlock().getId() == b.getId()).collect(Collectors.toList());

        Set<Section> studentSections = student.getSections();

        for (Section sSec:studentSections) {
            if(sectionList.contains(sSec))
                sectionList.remove(sSec);
        }

       /* List<Section> tempSec = new ArrayList<Section>();
        for (Section sSec:studentSections) {
            Integer bid = sSec.getBlock().getId();

            for (Section c:sectionList) {
                if(c.getBlock().getId() != bid)
                    tempSec.add(c);
            }
        }
        sectionList = tempSec;*/


        //  sectionList.stream().filter(s->s.equals())

        model.addAttribute("sectionRegistration", student);//student object
        model.addAttribute("sectionList", sectionList);//available sections
        //model.addAttribute("blocks", entry.getBlocks());//available sections
        model.addAttribute("studentSections", studentSections);//sections already registered for

        // studentSections

        return "section/registerSection";
    }


    @PostMapping("/saveSecRegistration")
    public String saveRegistration(@ModelAttribute("sectionRegistration") Student student,
                            Model model, RedirectAttributes redirectAttributes, Principal principal){

        if(principal == null)
            return "redirect:/login";
        String studentEmail = principal.getName();

        Student studentToPersist = iStudentService.findByEmail(studentEmail);


        Section sec = student.getSections().iterator().next();
        Set<Section> studentSections = studentToPersist.getSections();
        for (Section s:studentSections) {
            if(s.getBlock().getId() == sec.getBlock().getId()){
                System.out.println(s.getBlock().getId()+"******************************"+sec.getBlock().getId());
                redirectAttributes.addFlashAttribute("errorMes", "You already register for a section in the Block: " + sec.getBlock().getBlockName());
                return "redirect:/student/register";
            }
        }
        //System.out.println("LAST ******************************"+sec.getBlock().getId());

        System.out.println(sec.getStudents().size() +"  size**************************capacity     "+ sec.getCapacity());
        if(sec.getStudents().size() >= sec.getCapacity()){
            redirectAttributes.addFlashAttribute("studentSections", studentSections );
            redirectAttributes.addFlashAttribute("errorMes", "Section reach capacity choose another!");
            return "redirect:/student/register";
        }


        studentToPersist.addSection(sec);
        iStudentService.save(studentToPersist);

        /*for (Section s: studentToPersist.getSections()) {
            System.out.println("\n\n\nFirst*****************"+ s.getName() + "**********************************************************\n\n");
        }*/


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
