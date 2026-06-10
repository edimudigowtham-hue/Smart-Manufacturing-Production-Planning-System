package com.genc.smpps.controller;


import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.service.ProductionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProductionOrderController {
    @Autowired
    private ProductionOrderService service;

    @PostMapping("/createProductionOrder")
    public String createOrder(@ModelAttribute ProductionOrder order) {
        service.createProductionOrder(order);
        return "redirect:/orders-page";
    }


}