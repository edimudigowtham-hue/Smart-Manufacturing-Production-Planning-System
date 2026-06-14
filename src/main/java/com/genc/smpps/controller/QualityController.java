package com.genc.smpps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.QualityInspection;
import com.genc.smpps.service.QualityService;

@Controller
@RequestMapping("/quality")
public class QualityController {

    @Autowired
    private QualityService service;


    @GetMapping("/page")
    public String qualityPage(Model model) {
        model.addAttribute("inspections", service.getAllInspections());
        return "quality";
    }


    @PostMapping("/inspect")
    public String inspect(@ModelAttribute QualityInspection q) {
        service.recordInspection(q);
        return "redirect:/quality/page";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam int id) {
        service.approveBatch(id);
        return "redirect:/quality/page";
    }


    @PostMapping("/reject")
    public String reject(@RequestParam int id) {
        service.rejectBatch(id);
        return "redirect:/quality/page";
    }
}
