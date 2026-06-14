package com.genc.smpps.controller;


import com.genc.smpps.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    private ProductMasterService productService;
    @Autowired
    private ProductionOrderService productionOrderService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private QualityService qualityService;
    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("maintenanceCount", maintenanceService.getAllWorkOrders().size());

        return "index";
    }


    @GetMapping("/maintenance-page")
    public String maintenancePage(Model model) {
        model.addAttribute("orders", maintenanceService.getAllWorkOrders());
        return "maintenance";
    }
}