package com.genc.smpps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.repo.ProductionOrderRepository;

@Service
public class ProductionOrderService {

    @Autowired
    private ProductionOrderRepository repo;

    // Create Order
    public ProductionOrder createProductionOrder(ProductionOrder order) {
        order.setOrderStatus("PLANNED");
        order.setProducedQuantity(0); // ensure default
        return repo.save(order);
    }

    // Release Order
    public ProductionOrder releaseOrder(int id) {
        ProductionOrder order = repo.findById(id).orElse(null);

        if (order != null) {
            order.setOrderStatus("RELEASED");
            return repo.save(order);
        }

        return null;
    }

    // Get All Orders
    public List<ProductionOrder> getAllOrders() {
        return repo.findAll();
    }

    // Schedule Work Center (PDF - mock)
    public String scheduleWorkCenter() {
        return " Work center scheduled successfully";
    }

    // Get Order Progress (Improved)
    public String getOrderProgress(int id) {
        ProductionOrder order = repo.findById(id).orElse(null);

        if (order != null) {
            return "Order ID: " + id +
                    ", Status: " + order.getOrderStatus() +
                    ", Produced: " + order.getProducedQuantity() +
                    "/" + order.getPlannedQuantity();
        }

        return " Order not found";
    }
    public ProductionOrder getOrderById(int id) {
        return repo.findById(id).orElse(null);
    }
    public ProductionOrder updateOrder(ProductionOrder updatedOrder) {

        ProductionOrder existing = repo.findById(updatedOrder.getOrderId()).orElse(null);

        if (existing != null) {
            existing.setProductId(updatedOrder.getProductId());
            existing.setPlannedQuantity(updatedOrder.getPlannedQuantity());
            existing.setProducedQuantity(updatedOrder.getProducedQuantity());
            existing.setStartDate(updatedOrder.getStartDate());
            existing.setEndDate(updatedOrder.getEndDate());
            existing.setOrderStatus(updatedOrder.getOrderStatus());

            return repo.save(existing);
        }

        return null;
    }

}