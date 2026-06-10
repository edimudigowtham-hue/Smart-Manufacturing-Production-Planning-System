package com.genc.smpps.service;


import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.repo.ProductionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}