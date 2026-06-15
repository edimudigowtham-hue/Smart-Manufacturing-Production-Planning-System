package com.genc.smpps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.MaintenanceWorkOrder;
import com.genc.smpps.service.MaintenanceService;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService service;

    // Load Page
    @GetMapping("/page")
    public String maintenancePage(Model model) {
        model.addAttribute("orders", service.getAllWorkOrders());
        return "maintenance";
    }

    // createWorkOrder
    @PostMapping("/create")
    public String create(@ModelAttribute MaintenanceWorkOrder w) {
        service.createWorkOrder(w);
        return "redirect:/maintenance/page";
    }

    // assignTechnician
    @PostMapping("/assign")
    public String assign(@RequestParam int id,
                         @RequestParam String technician) {

        service.assignTechnician(id, technician);
        return "redirect:/maintenance/page";
    }

    // issueSpare
    @PostMapping("/spare")
    public String spare(@RequestParam int id,
                        @RequestParam String spareParts) {

        service.issueSpare(id, spareParts);
        return "redirect:/maintenance/page";
    }

    // closeWorkOrder
    @PostMapping("/close")
    public String close(@RequestParam int id) {
        service.closeWorkOrder(id);
        return "redirect:/maintenance/page";
    }
}