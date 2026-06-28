package com.genc.smpps.controller;

import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.model.dto.OrderProgressResponse;
import com.genc.smpps.service.ProductionOrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class ProductionOrderController {

    private final ProductionOrderService service;

    public ProductionOrderController(ProductionOrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductionOrder> getOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ProductionOrder getOrder(@PathVariable int id) {
        return service.findOrderByIdOrThrow(id);
    }

    @PostMapping
    public ProductionOrder createOrder(@Valid @RequestBody ProductionOrder order) {
        return service.createProductionOrder(order);
    }

    @PutMapping("/{id}")
    public ProductionOrder updateOrder(@PathVariable int id, @Valid @RequestBody ProductionOrder order) {
        order.setOrderId(id);
        return service.updateOrder(order);
    }

    @GetMapping("/{id}/progress")
    public OrderProgressResponse progress(@PathVariable int id) {
        return service.getOrderProgress(id);
    }

    @PostMapping("/{id}/release")
    public ProductionOrder releaseOrder(@PathVariable int id) {
        return service.releaseOrder(id);
    }

    @PostMapping("/{id}/start")
    public ProductionOrder startOrder(@PathVariable int id) {
        return service.startOrder(id);
    }

    @PostMapping("/{id}/complete")
    public ProductionOrder completeOrder(@PathVariable int id) {
        return service.completeOrder(id);
    }

    @PostMapping("/{id}/cancel")
    public ProductionOrder cancelOrder(@PathVariable int id) {
        return service.cancelOrder(id);
    }

}