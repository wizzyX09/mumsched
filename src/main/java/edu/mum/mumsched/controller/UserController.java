package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Role;
import edu.mum.mumsched.model.User;
import edu.mum.mumsched.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private IFacultyService iFacultyService;

    public final static String ROLE_STUDENT = "STUDENT";
    public final static String ROLE_FACULTY = "FACULTY";
    public final static String ROLE_ADMIN = "ADMIN";

    @GetMapping("/allUsers")
    public String findAll(Model model) {
        model.addAttribute("allUser", userService.findAll());
        return "user/allUser";
    }

    @GetMapping("/detailsUser/{id}")
    public String detailsUser(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "user/details";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserForm(@PathVariable("id") Integer id){
        userService.delete(id);
        return "redirect:/allUsers";
    }

    @GetMapping("/newUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listOfRoles", iRoleService.findAllExcept(ROLE_STUDENT, ROLE_FACULTY));
//        model.addAttribute("listOfRoles", iRoleService.findAll());
        return "user/form";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "user/form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        bindingResult.hasErrors();
        userService.updateUser(user);
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

    @GetMapping("/user/accountType")
    public String accountTypeForm(Model model){
        model.addAttribute("roles", Arrays.asList(ROLE_STUDENT, ROLE_FACULTY, ROLE_ADMIN));
        return "user/accountTypeForm";
    }

    @PostMapping("/user/generateAccount")
    public String generateAccount(@RequestParam("accountType") String accountType, Model model){
        if(accountType.equals(ROLE_STUDENT)) {
            return generateStudentList(model);
        } else if(accountType.equals(ROLE_FACULTY)) {
            return generateFacultyList(model);
        }
        return "redirect:/newUser";
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

    @GetMapping("/user/generateFacultyList")
    public String generateFacultyList(Model model) {
        model.addAttribute("list", iFacultyService.findAll());
        return "user/generateFacultyList";
    }

    @GetMapping("/user/generateFaculty/{id}")
    public String generateFaculty(@PathVariable("id") Integer id, Model model){
        userService.generateUserByFaculty(iFacultyService.findById(id));
        return "redirect:/allUsers";
    }

  /*  @GetMapping("/403")
    public String _403(){
        return"403";
    }*/
}
