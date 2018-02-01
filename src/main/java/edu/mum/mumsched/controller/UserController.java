package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.*;
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
    private UserService<User, UserForm> userService;

    @Autowired
    private UserService<Admin, UserForm> adminService;

    @Autowired
    private UserService<Faculty, UserForm> facultyService;

    @Autowired
    private UserService<Student, UserForm> studentService;

    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/allUsers")
    public String findAll(Model model) {
        model.addAttribute("allUser", userService.findAll());
        return "allUser";
    }

    @GetMapping("/newUser")
    public String addUser(Model model) {
        model.addAttribute("user", new UserForm());
        model.addAttribute("listOfRoles", iRoleService.findAll());
        return "addUserForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid UserForm userForm, BindingResult bindingResult) {
        bindingResult.hasErrors();
        userService.save(userForm);
        return "redirect:/allUsers";
    }

    // In order to map the selected user type in the UI (admin, faculty, student). we are sending integer value from UI.
    // And on the controller side, we are using InitBinder that maps the integer value to it's respected role object.
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

    public UserService getServiceByEntity(UserForm userForm) {
        if (userForm.isAdmin()) return adminService;
        else if (userForm.isFaculty()) return facultyService;
        else if (userForm.isStudent()) return studentService;
        return userService;
    }
}
