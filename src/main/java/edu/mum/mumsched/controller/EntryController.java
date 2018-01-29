package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Entry;
import edu.mum.mumsched.service.IEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EntryController {

    @Autowired
    private IEntryService iEntryService;

    @GetMapping("/allEntry")
    public String findAll(Model model){
        model.addAttribute("allEntry",iEntryService.findAll());
        return "entry/manage";
    }

    @GetMapping("/newEntry")
    public String addEntryForm(Model model){
        model.addAttribute("entry",new Entry());
        return "entry/form";
    }

    @GetMapping("/deleteEntry/{id}")
    public String deleteEntryForm(@PathVariable("id") Integer id){
        iEntryService.delete(id);
        return "redirect:/allEntry";
    }

    @GetMapping("/updateEntry/{id}")
    public String updateEntryForm(@PathVariable("id") Integer id,Model model){
        model.addAttribute("entry",iEntryService.findById(id));
        return "entry/form";
    }

    @PostMapping("/saveEntry")
    public String saveEntry(@ModelAttribute @Valid Entry entry, BindingResult bindingResult){
        bindingResult.hasErrors();
        iEntryService.save(entry);
        return "redirect:/allEntry";
    }

}
