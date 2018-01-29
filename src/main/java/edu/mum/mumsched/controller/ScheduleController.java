package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @GetMapping("/manage")
    public String  manage(Model model){
        model.addAttribute("schedules",scheduleRepository.findAll());
        return "schedule/manage";
    }

    @GetMapping("/create")
    public String  createForm(Model model){
        model.addAttribute("schedule",new Schedule());
        return "schedule/manage";
    }

}
