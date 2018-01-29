package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.repository.EntryRepository;
import edu.mum.mumsched.repository.ScheduleRepository;
import edu.mum.mumsched.service.IEntryService;
import edu.mum.mumsched.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IScheduleService iScheduleService;
    @Autowired
    IEntryService iEntryService;

    @GetMapping("/manage")
    public String  manage(Model model){
        model.addAttribute("schedules",iScheduleService.findAll());
        return "schedule/manage";
    }
    @GetMapping("/create")
    public String  createForm(Model model){
        System.out.println("/schedule/create/get");
        model.addAttribute("schedule",new Schedule());
        model.addAttribute("entries",iEntryService.findEntryWithoutSchedule());
        return "schedule/add";
    }
    @GetMapping("/edit")
    public String  editForm(Model model){
        return "redirect:schedule/edit";
    }
    @PostMapping("/create")
    public String  create(@ModelAttribute Schedule schedule){
        System.out.println("Entry:"+schedule.getEntry().getId());
        return "redirect:schedule/manage";
    }


}
