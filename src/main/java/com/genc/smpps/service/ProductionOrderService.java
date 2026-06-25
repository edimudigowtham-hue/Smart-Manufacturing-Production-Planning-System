package com.genc.smpps.service;

import java.util.List;

import com.genc.smpps.model.FinishedProduct;
import com.genc.smpps.model.OrderStatus;
import org.springframework.stereotype.Service;
import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.repo.ProductionOrderRepository;
import com.genc.smpps.repo.ProductRepository;

@Service
public class ProductionOrderService {

    private final ProductionOrderRepository repo;
    private final ProductRepository productRepository;

    public ProductionOrderService(ProductionOrderRepository repo, ProductRepository productRepository) {
        this.repo = repo;
        this.productRepository = productRepository;
    }


    public ProductionOrder createProductionOrder(ProductionOrder order) {
        FinishedProduct product = findProductOrThrow(order.getProductId());
        order.setProduct(product);
        order.setOrderStatus(OrderStatus.PLANNED);
        order.setProducedQuantity(0); // ensure default
        return repo.save(order);
    }


    public ProductionOrder releaseOrder(Integer id) {
        ProductionOrder order = findOrderOrThrow(id);
        if (order.getOrderStatus() != OrderStatus.PLANNED) {
            throw new IllegalStateException("Only PLANNED orders can be released");
        }

        order.setOrderStatus(OrderStatus.RELEASED);
        return repo.save(order);
    }


    public ProductionOrder startOrder(Integer id) {
        ProductionOrder order = findOrderOrThrow(id);
        if (order.getOrderStatus() != OrderStatus.RELEASED) {
            throw new IllegalStateException("Only RELEASED orders can be started");
        }

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        return repo.save(order);
    }


    public ProductionOrder completeOrder(Integer id) {
        ProductionOrder order = findOrderOrThrow(id);
        if (order.getOrderStatus() != OrderStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only IN_PROGRESS orders can be completed");
        }
        if (order.getProducedQuantity() == null || order.getPlannedQuantity() == null) {
            throw new IllegalStateException("Produced and planned quantities are required before completion");
        }
        if (!order.getProducedQuantity().equals(order.getPlannedQuantity())) {
            throw new IllegalStateException("Produced quantity must match planned quantity before completion");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        return repo.save(order);
    }


    public ProductionOrder cancelOrder(Integer id) {
        ProductionOrder order = findOrderOrThrow(id);
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Completed orders cannot be cancelled");
        }
        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        return repo.save(order);
    }


    public List<ProductionOrder> getAllOrders() {
        return repo.findAll();
    }


    public String getOrderProgress(Integer id) {
        if (id == null || id <= 0) {
            return "Invalid order ID";
        }

        ProductionOrder order = repo.findById(id).orElse(null);

        if (order != null) {
            return "Order ID: " + id +
                    ", Status: " + order.getOrderStatus() +
                    ", Produced: " + order.getProducedQuantity() +
                    "/" + order.getPlannedQuantity();
        }

        return " Order not found";
    }


    public ProductionOrder getOrderById(Integer id) {
        return repo.findById(id).orElse(null);
    }


    public ProductionOrder updateOrder(ProductionOrder updatedOrder) {

        ProductionOrder existing = findOrderOrThrow(updatedOrder.getOrderId());

        if (existing.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Completed orders cannot be updated");
        }
        if (existing.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled orders cannot be updated");
        }

        FinishedProduct product = findProductOrThrow(updatedOrder.getProductId());

        existing.setProduct(product);
        existing.setPlannedQuantity(updatedOrder.getPlannedQuantity());
        existing.setProducedQuantity(updatedOrder.getProducedQuantity());
        existing.setStartDate(updatedOrder.getStartDate());
        existing.setEndDate(updatedOrder.getEndDate());

        return repo.save(existing);
    }


    private ProductionOrder findOrderOrThrow(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0");
        }
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }


    private FinishedProduct findProductOrThrow(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than 0");
        }
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }


}