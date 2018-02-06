package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IEntryService;
import edu.mum.mumsched.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    IEntryService iEntryService;
    @Autowired
    private IScheduleService iScheduleService;
    @Autowired
    private ICourseService iCourseService;

    @GetMapping("/manage")
    public String manage(Model model) {
        model.addAttribute("schedules", iScheduleService.findAll());
        return "schedule/manage";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("entries", iEntryService.findEntryWithoutSchedule());
        return "schedule/add";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Schedule schedule=iScheduleService.findById(id);
        if(schedule==null)return "redirect:/schedule/manage";
        model.addAttribute("schedule",schedule);
        return "schedule/edit";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Schedule schedule,Model model) {
        try {
            iScheduleService.saveOrUpdate(schedule.generate(iCourseService.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message","Schedule generated");
        return "redirect:/schedule/manage";
    }


}
