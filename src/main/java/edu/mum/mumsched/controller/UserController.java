package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Role;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.service.IRoleService;
import edu.mum.mumsched.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

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

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(List.class, "roles", new CustomCollectionEditor(List.class) {

            protected Object convertElement(Object element) {
                if (element != null) {
                    Integer roleId = Integer.parseInt(element.toString());
                    Role role = iRoleService.findById(roleId);
                    return role;
                }
                return null;
            }
        });
    }
}
