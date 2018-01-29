package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.model.Section;
import edu.mum.mumsched.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SectionController {

    @Autowired
    ISectionService iSectionService;

    //Display manage section page, show listing of sections in db
    @RequestMapping(value="/section/manage",method = RequestMethod.GET)
    public String manageSection(Model model){
        model.addAttribute("allSection",iSectionService.findAll());
        return "section/manage";
    }
    //Display section Form
    @RequestMapping(value="/section/addSectionForm",method = RequestMethod.GET)
    public String addNewSection(Model model){
        model.addAttribute("newSection",new Section());
        return "section/addSectionForm";
    }

    @RequestMapping(value="/section/saveSection",method = RequestMethod.POST)
    public String saveSection(@ModelAttribute("newSection") Section section,
                              Model model, RedirectAttributes redirectAttributes){

        iSectionService.save(section);

        redirectAttributes.addFlashAttribute("message",  section.getName() + " Section successfully save!");
        return "redirect:/section/manage";
    }
}
