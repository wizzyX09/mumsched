package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.service.IBlockService;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SectionController {

    @Autowired
    ISectionService iSectionService;
    @Autowired
    IBlockService iBlockService;
    @Autowired
    ICourseService iCoursesService;




    //Display manage section page, show listing of sections in db
    @RequestMapping(value="/section/manage",method = RequestMethod.GET)
    public String manageSection(Model model){
        model.addAttribute("allSection",iSectionService.findAll());
        return "section/manage";
    }
    //Display section Form
    @RequestMapping(value="/section/form",method = RequestMethod.GET)
    public String addNewSection(Model model){
        model.addAttribute("sectionForm",new Section());
        model.addAttribute("blockList",iBlockService.findAll());
        model.addAttribute("courseList",iCoursesService.findAll());
        //model.addAttribute("facultyList",null);
        return "section/form";
    }

    @RequestMapping(value="/section/saveSection",method = RequestMethod.POST)
    public String saveSection(@ModelAttribute("sectionForm") Section section,
                              Model model, RedirectAttributes redirectAttributes){

        iSectionService.save(section);

        redirectAttributes.addFlashAttribute("message",  section.getName() + " Section successfully save!");
        return "redirect:/section/manage";
    }
}
