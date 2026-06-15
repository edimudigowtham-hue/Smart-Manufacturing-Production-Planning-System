package com.genc.smpps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.MachineLog;
import com.genc.smpps.service.MachineService;

@Controller
@RequestMapping("/machine")
public class MachineController {

    @Autowired
    private MachineService service;

    // Page
    @GetMapping("/page")
    public String machinePage(Model model) {
        model.addAttribute("logs", service.getAllLogs());
        return "machine";
    }

    // recordRuntime
    @PostMapping("/runtime")
    public String runtime(@ModelAttribute MachineLog log) {
        service.recordRuntime(log);
        return "redirect:/machine/page";
    }

    // logDowntime
    @PostMapping("/downtime")
    public String downtime(@ModelAttribute MachineLog log) {
        service.logDowntime(log);
        return "redirect:/machine/page";
    }

}
