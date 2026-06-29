package com.genc.smpps.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.MaintenanceWorkOrder;
import com.genc.smpps.service.MaintenanceService;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {


    private final MaintenanceService service;

    @Autowired
    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    private void populateMaintenancePageModel(Model model) {
        if (!model.containsAttribute("workOrder")) {
            model.addAttribute("workOrder", new MaintenanceWorkOrder());
        }
        if (!model.containsAttribute("assignForm")) {
            model.addAttribute("assignForm", new MaintenanceWorkOrder());
        }
        if (!model.containsAttribute("spareForm")) {
            model.addAttribute("spareForm", new MaintenanceWorkOrder());
        }
        if (!model.containsAttribute("closeForm")) {
            model.addAttribute("closeForm", new MaintenanceWorkOrder());
        }
        model.addAttribute("orders", service.getAllWorkOrders());
    }
    // Load Page
    @GetMapping("/page")
    public String maintenancePage(Model model) {
        populateMaintenancePageModel(model);
        return "maintenance";
    }

    // createWorkOrder
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("workOrder") MaintenanceWorkOrder w,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        service.createWorkOrder(w);
        return "redirect:/maintenance/page";
    }

    // assignTechnician
    @PostMapping("/assign")
    public String assign(@Validated(MaintenanceWorkOrder.OnAssign.class)
                         @ModelAttribute("assignForm") MaintenanceWorkOrder form,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        MaintenanceWorkOrder updatedWorkOrder = service.assignTechnician(form.getWorkOrderId(), form.getTechnician());

        if (updatedWorkOrder == null) {
            bindingResult.rejectValue("workOrderId", "notFound", "Work order not found");
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        return "redirect:/maintenance/page";
    }

    // issueSpare
    @PostMapping("/spare")
    public String spare(@Validated(MaintenanceWorkOrder.OnSpare.class)
                        @ModelAttribute("spareForm") MaintenanceWorkOrder form,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        MaintenanceWorkOrder updatedWorkOrder = service.issueSpare(form.getWorkOrderId(), form.getSpareParts());

        if (updatedWorkOrder == null) {
            bindingResult.rejectValue("workOrderId", "notFound", "Work order not found");
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        return "redirect:/maintenance/page";
    }

    // closeWorkOrder
    @PostMapping("/close")
    public String close(@Validated(MaintenanceWorkOrder.OnClose.class)
                        @ModelAttribute("closeForm") MaintenanceWorkOrder form,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        MaintenanceWorkOrder updatedWorkOrder = service.closeWorkOrder(form.getWorkOrderId());

        if (updatedWorkOrder == null) {
            bindingResult.rejectValue("workOrderId", "notFound", "Work order not found");
            populateMaintenancePageModel(model);
            return "maintenance";
        }

        return "redirect:/maintenance/page";
    }


}
