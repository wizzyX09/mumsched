package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Faculty;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.model.UserForm;
import edu.mum.mumsched.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    ISectionService iSectionService;
    @Autowired
    IBlockService iBlockService;
    @Autowired
    ICourseService iCoursesService;
    @Autowired
    UserService<Faculty, UserForm> facultyService;
    
    //Display manage section page, show listing of sections in db
    @RequestMapping(value="/manage",method = RequestMethod.GET)
    public String manageSection(Model model){
        model.addAttribute("allSection",iSectionService.findAll());
        return "section/manage";
    }
    //Display section Form
    @RequestMapping(value="/form",method = RequestMethod.GET)
    public String addNewSection(Model model){
        model.addAttribute("sectionForm",new Section());
        model.addAttribute("blockList",iBlockService.findAll());
        model.addAttribute("courseList",iCoursesService.findAll());
        model.addAttribute("facultyList", facultyService.findAll());
        model.addAttribute("title","Adding New Section");

        return "section/form";
    }

    @RequestMapping(value="/saveSection",method = RequestMethod.POST)
    public String saveSection(@ModelAttribute("sectionForm") Section section,
                              Model model, RedirectAttributes redirectAttributes){

        iSectionService.save(section);

        redirectAttributes.addFlashAttribute("message",  section.getName() + " Section successfully save!");
        return "redirect:/section/manage";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String updateSection(@PathVariable("id")Integer id, Model model){
        model.addAttribute("sectionForm",iSectionService.findById(id));
        model.addAttribute("blockList",iBlockService.findAll());
        model.addAttribute("courseList",iCoursesService.findAll());
        model.addAttribute("facultyList", facultyService.findAll());
        model.addAttribute("title","Updating Section");
        return "section/form";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteSection(@PathVariable("id")Integer id, RedirectAttributes redirectAttributes){
        iSectionService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Section successfully delete!");
        return "redirect:/section/manage";
    }
}
