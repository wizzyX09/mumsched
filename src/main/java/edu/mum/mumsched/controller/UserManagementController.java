package edu.mum.mumsched.controller;

import edu.mum.mumsched.service.IEmailService;
import edu.mum.mumsched.service.IFacultyService;
import edu.mum.mumsched.service.IStudentService;
import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserManagementController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IFacultyService iFacultyService;

    @Autowired
    private UserService userService;

    @Autowired
    private IEmailService iEmailService;

    @GetMapping("/user/accountType")
    public String accountTypeForm(Model model){
        model.addAttribute("");
        return "user/accountTypeForm";
    }

    @PostMapping("/user/generateAccount")
    public String generateAccount(@RequestParam("accountType") String accountType, Model model){
        if(accountType.equals("STUDENT")) {
            return generateStudentList(model);
        }
        return generateStudentList(model);
    }

    @GetMapping("/user/generateStudentList")
    public String generateStudentList(Model model) {
        model.addAttribute("list", iStudentService.findAll());
        return "user/generateStudentList";
    }

    @GetMapping("/user/generateStudent/{id}")
    public String generateStudent(@PathVariable("id") Integer id, Model model){
        userService.generateUserByStudent(iStudentService.findById(id));
        return "redirect:/allUsers";
    }

//    @GetMapping("/user/generateFacultyList")
//    public String generateFacultyList(Model model) {
//        model.addAttribute("list", iFacultyService.findAllByUserIsNull());
//        return "user/generateFacultyList";
//    }
//
//    @GetMapping("/user/generateFaculty/{id}")
//    public String generateFaculty(@PathVariable("id") Integer id, Model model){
//        userService.generateUserByFaculty(iFacultyService.findById(id));
//        return "redirect:/allUsers";
//    }
}
