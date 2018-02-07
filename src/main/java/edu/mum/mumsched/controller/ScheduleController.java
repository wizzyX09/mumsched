package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Schedule;
import edu.mum.mumsched.model.ScheduleStatus;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IEntryService;
import edu.mum.mumsched.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createForm(Model model,@ModelAttribute("exception") final String exception) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("entries", iEntryService.findEntryWithoutSchedule());
        model.addAttribute("exception",exception);
        return "schedule/add";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Schedule schedule=iScheduleService.findById(id);
        if(schedule==null)return "redirect:/schedule/manage";
        model.addAttribute("schedule",schedule);
        return "schedule/edit";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Schedule schedule=iScheduleService.findById(id);
        if(schedule==null)return "redirect:/schedule/manage";
        model.addAttribute("schedule",schedule);
        return "schedule/details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,Model model) {
        Schedule schedule=iScheduleService.findById(id);
        try {
            iScheduleService.delete(schedule);
        }catch(Exception ex){
            ex.printStackTrace();
        }
      //model.addAttribute("message","Schedule generated");
        return "redirect:/schedule/manage";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Schedule schedule,Model model) {
        try {
            iScheduleService.saveOrUpdate(schedule.generate(iCourseService.findAll()));
        }catch(RuntimeException e){
            e.printStackTrace();
        }
        model.addAttribute("message","Schedule generated");
        return "redirect:/schedule/manage";
    }


    @GetMapping("/approved/{id}")
    public String approved(@PathVariable("id") Integer id, RedirectAttributes model) {
        try {
           Schedule schedule= iScheduleService.findById(id);
           schedule.setStatus(ScheduleStatus.APPROVED);
           schedule.onApproved();
           iScheduleService.saveOrUpdate(schedule);
        }catch(RuntimeException e){
            e.printStackTrace();
        }

        model.addAttribute("message","Schedule Approved successfully");
        return "redirect:/schedule/manage";
    }


}
