package edu.mum.mumsched.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value =Exception.class)
    public String defaultExceptionHandler(Model model, Exception ex){

       if( ex.getMessage()!=null)
           model.addAttribute("exception",ex.getMessage());
        else
            model.addAttribute("exception","Error occurred, cause unknown");
        return "shared/exception";
    }
    @ExceptionHandler(value =RuntimeException.class)
    public String defaultRuntimeExceptionHandler(final RedirectAttributes model,RuntimeException ex, HttpServletRequest request){
        if( ex.getMessage()!=null)
            model.addFlashAttribute("exception",ex.getMessage());
        else
            model.addFlashAttribute("exception","Error occurred, cause unknown");
       if(request.getRequestURI().contains("/schedule/delete"))
           return "redirect:/schedule/manage";

        return "redirect:"+request.getRequestURI();
    }
}
