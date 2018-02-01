package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Block;
import edu.mum.mumsched.service.IBlockService;
import edu.mum.mumsched.service.IEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BlockController {

    @Autowired
    private IBlockService iBlockService;

    @Autowired
    private IEntryService iEntryService;

    @GetMapping("/allBlock")
    public String findAll(Model model){
        model.addAttribute("allBlock",iBlockService.findAll());
        return "block/manage";
    }

    @GetMapping("/newBlock")
    public String addBlockForm(Model model) {
        model.addAttribute("block", new Block());
        model.addAttribute("entryList", iEntryService.findAll());
        return "block/form";
    }

    @PostMapping("/saveBlock")
    public String saveBlockForm(@Valid Block block, BindingResult bindingResult) {
        bindingResult.hasErrors();
        iBlockService.save(block);
        return "redirect:/allBlock";
    }

    @GetMapping("/deleteBlock/{id}")
    public String deleteBlockForm(@PathVariable("id") Integer id) {
        iBlockService.delete(id);
        return "redirect:/allBlock";
    }

    @GetMapping("/updateBlock/{id}")
    public String updateBlockForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("block", iBlockService.findById(id));
        model.addAttribute("entryList", iEntryService.findAll());
        return "block/form";
    }
}
