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
    public String displaySchedules(Model model,@ModelAttribute("exception") String exception) {
        model.addAttribute("schedules", iScheduleService.findAll());
        return "schedule/manage";
    }

    @GetMapping("/create")
    public String createScheduleForm(Model model,@ModelAttribute("exception") final String exception) {
        model.addAttribute("entries", iEntryService.findEntryWithoutSchedule());
        model.addAttribute("schedule", new Schedule());
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
    public String findScheduleDetails(@PathVariable Integer id, Model model) {
        Schedule schedule=iScheduleService.findById(id);
        if(schedule==null)return "redirect:/schedule/manage";
        model.addAttribute("schedule",schedule);
        return "schedule/details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,Model model) {
        Schedule schedule=iScheduleService.findById(id);
            iScheduleService.delete(schedule);
        return "redirect:/schedule/manage";
    }

    @PostMapping("/create")
    public String generateSchedule(@ModelAttribute Schedule schedule,Model model) {
        iScheduleService.saveOrUpdate(schedule.generate(iCourseService.findAll()));
        model.addAttribute("message","Schedule generated");
        return "redirect:/schedule/manage";
    }


    @GetMapping("/approved/{id}")
    public String approved(@PathVariable("id") Integer id, RedirectAttributes model) {
           Schedule schedule= iScheduleService.findById(id);
           if(schedule!=null) {
               schedule.setStatus(ScheduleStatus.APPROVED);
               schedule.onApproved();
               schedule.checkIfEachSectionHasFaculty();
               iScheduleService.saveOrUpdate(schedule);
           }
           return "redirect:/schedule/manage";
    }

    @GetMapping("/unapproved/{id}")
    public String unapproved(@PathVariable("id") Integer id, RedirectAttributes model) {
        Schedule schedule= iScheduleService.findById(id);
        if(schedule!=null) {
            schedule.setStatus(ScheduleStatus.DRAFT);
            iScheduleService.saveOrUpdate(schedule);
        }
        return "redirect:/schedule/manage";
    }


}
