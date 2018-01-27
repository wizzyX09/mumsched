package edu.mum.mumsched.controller;

import edu.mum.mumsched.service.IBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlockController {

    @Autowired
    private IBlockService iBlockService;

    @GetMapping("/allBlock")
    public String findAll(Model model){
        model.addAttribute("allBlock",iBlockService.findAll());
        return "allBlock";
    }


}
