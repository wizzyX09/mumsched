package edu.mum.mumsched.controller;

import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allUsers")
    public String findAll(Model model) {
        model.addAttribute("allUser", userService.findAll());
        return "allUser";
    }
}
