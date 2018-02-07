package edu.mum.mumsched.controller;

import edu.mum.mumsched.exception.EntryException;
import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.service.IBlockService;
import edu.mum.mumsched.service.IEntryService;
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

@Controller
public class EntryController {

    @Autowired
    private IEntryService iEntryService;

    @Autowired
    private IBlockService iBlockService;

    @GetMapping("/allEntry")
    public String findAll(Model model){
        model.addAttribute("allEntry",iEntryService.findAllOrdered());
        return "entry/manage";
    }

    @GetMapping("/newEntry")
    public String addEntryForm(Model model){
        model.addAttribute("entry",new Entry());
        model.addAttribute("blockList", iBlockService.findAllByOrdered());
        return "entry/form";
    }

    @GetMapping("/deleteEntry/{id}")
    public String deleteEntryForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        Entry entry = iEntryService.findById(id);
        if(entry.getSchedule() == null) {
            entry.setBlocks(null);
            iEntryService.save(entry);
            iEntryService.delete(id);
        } else {
            redirectAttributes.addFlashAttribute("messageError",
                    "The entry was not deleted! Schedule is created. " +
                            "You cannot delete entry once schedule is created.");
        }
        return "redirect:/allEntry";
    }

    @GetMapping("/updateEntry/{id}")
    public String updateEntryForm(@PathVariable("id") Integer id,Model model){
        model.addAttribute("entry",iEntryService.findById(id));
        model.addAttribute("blockList", iBlockService.findAllByOrdered());
        return "entry/form";
    }

    @PostMapping("/saveEntry")
    public String saveEntry(@ModelAttribute @Valid Entry entry,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes){
        bindingResult.hasErrors();
        iEntryService.save(entry);
        redirectAttributes.addFlashAttribute("messageSuccess","The entry was saved successful!");
        return "redirect:/allEntry";
    }

}
