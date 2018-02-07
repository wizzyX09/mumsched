package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Block;
import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.service.IBlockService;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mum.mumsched.SectionRegistrationSubsystem.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/section")
public class SectionController {

    //@Autowired
    //ISectionService iSectionService;
    @Autowired
    IBlockService iBlockService;
    @Autowired
    ICourseService iCoursesService;
    @Autowired
    IFacultyService iFacultyService;

    @Autowired
    ISectionRegistrationSubsystem sectionRegistrationFacade;


    //Display manage section page, show listing of sections in db
    @RequestMapping(value="/manage",method = RequestMethod.GET)
    public String manageSection(Model model){
        model.addAttribute("allSection",sectionRegistrationFacade.findAll());//iSectionService.findAll());
        return "section/manage";
    }
    //Display section Form
    @RequestMapping(value="/form",method = RequestMethod.GET)
    public String addNewSection(Model model){
        model.addAttribute("sectionForm",new Section());
        model.addAttribute("blockList",iBlockService.findAll());
        model.addAttribute("courseList",iCoursesService.findAll());
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("title","Adding New Section");

        return "section/form";
    }

    @RequestMapping(value="/saveSection",method = RequestMethod.POST)
    public String saveSection(@ModelAttribute("sectionForm") Section section,
                              Model model, RedirectAttributes redirectAttributes){
        Block block = section.getBlock();
        Faculty faculty = section.getFaculty();
        System.out.println("*****Block ID******************" + block.getId());

        Integer secId = section.getId()==null?0:section.getId();
        List<Section> blockSections = sectionRegistrationFacade.findByBlockId(block.getId(), secId);
        for (Section bSec: blockSections) {
            if(bSec.getFaculty() != null && bSec.getFaculty().getId() == faculty.getId()) {
                if (section.getId() == null) {

                    System.out.println("*****section ID******************" + section.getId());

                    redirectAttributes.addFlashAttribute("message", " A professor cannot appear twice in the same block!");
                    return "redirect:/section/form";
                } else {
                    redirectAttributes.addFlashAttribute("message", " A professor cannot appear twice in the same block!");
                    return "redirect:/section/update/" + section.getId();
                }
            }
        }

        sectionRegistrationFacade.save(section);
        redirectAttributes.addFlashAttribute("message",  section.getName() + " Section successfully save!");
        return "redirect:/section/manage";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateSection(@PathVariable("id")Integer id, Model model){
        model.addAttribute("sectionForm",sectionRegistrationFacade.findById(id));
        model.addAttribute("blockList",iBlockService.findAll());
        model.addAttribute("courseList",iCoursesService.findAll());
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("title","Updating Section");
        return "section/form";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteSection(@PathVariable("id")Integer id, RedirectAttributes redirectAttributes){
        sectionRegistrationFacade.delete(id);
        redirectAttributes.addFlashAttribute("message", "Section successfully delete!");
        return "redirect:/section/manage";
    }
}
