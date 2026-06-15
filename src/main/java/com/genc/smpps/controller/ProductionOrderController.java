package com.genc.smpps.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.service.ProductionOrderService;

@Controller
@RequestMapping("/orders")
public class ProductionOrderController {
    @Autowired
    private ProductionOrderService service;

    @PostMapping("/createProductionOrder")
    public String createOrder(@ModelAttribute ProductionOrder order) {
        service.createProductionOrder(order);
        return "redirect:/orders-page";
    }

    @Autowired
    private ProductionOrderService service;

    @PostMapping("/createProductionOrder")
    public String createOrder(@ModelAttribute ProductionOrder order) {
        service.createProductionOrder(order);
        return "redirect:/orders-page";
    }

    @PostMapping("/release")
    public String releaseOrder(@RequestParam int orderId) {
        service.releaseOrder(orderId);
        return "redirect:/orders-page";
    }

    @GetMapping("/progress")
    public String progress(@RequestParam int orderId, Model model) {
        String result = service.getOrderProgress(orderId);
        model.addAttribute("progressResult", result);
        return "order-progress";
    }

    @GetMapping("/schedule")
    @ResponseBody
    public String schedule() {
        return service.scheduleWorkCenter();
    }
    @GetMapping("/edit/{id}")
    public String editOrder(@PathVariable int id, Model model) {

        ProductionOrder order = service.getOrderById(id);
        model.addAttribute("order", order);

        return "edit-order";
    }
    @PostMapping("/update")
    public String updateOrder(@ModelAttribute ProductionOrder order) {
        service.updateOrder(order);
        return "redirect:/orders-page";
    }
}