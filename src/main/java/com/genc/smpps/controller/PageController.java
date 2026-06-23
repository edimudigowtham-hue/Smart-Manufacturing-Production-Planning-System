package com.genc.smpps.controller;

import com.genc.smpps.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

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

        model.addAttribute("productCount", productService.getAllProducts().size());
        model.addAttribute("orderCount", productionOrderService.getAllOrders().size());
        model.addAttribute("machineCount", machineService.getAllLogs().size());
        model.addAttribute("qualityCount", qualityService.getAllInspections().size());
        model.addAttribute("maintenanceCount", maintenanceService.getAllWorkOrders().size());

        return "index";
    }

    @GetMapping("/orders-page")
    public String orders(Model model) {
        model.addAttribute("orders", productionOrderService.getAllOrders());
        return "order";
    }


    @GetMapping("/machine-page")
    public String machinePage(Model model) {
        model.addAttribute("logs", machineService.getAllLogs());
        return "machine";
    }

    @GetMapping("/quality-page")
    public String qualityPage(Model model) {
        model.addAttribute("inspections", qualityService.getAllInspections());
        return "quality";
    }

    @GetMapping("/maintenance-page")
    public String maintenancePage(Model model) {
        model.addAttribute("orders", maintenanceService.getAllWorkOrders());
        return "maintenance";
    }

}