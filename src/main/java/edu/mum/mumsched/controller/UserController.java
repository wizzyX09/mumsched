package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.User;
import edu.mum.mumsched.service.IRoleService;
import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/allUsers")
    public String findAll(Model model) {
        model.addAttribute("allUser", userService.findAll());
        return "allUser";
    }

    @GetMapping("/newUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listOfRoles", iRoleService.findAll());
        return "addUserForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        bindingResult.hasErrors();
        userService.saveUser(user);
        return "redirect:/allUsers";
    }
}
