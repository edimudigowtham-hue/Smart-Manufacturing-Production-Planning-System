package com.genc.smpps.service;

import java.util.List;
import java.util.Objects;

import com.genc.smpps.exception.ResourceNotFoundException;
import com.genc.smpps.model.FinishedProduct;
import com.genc.smpps.model.InspectionResult;
import com.genc.smpps.model.OrderStatus;
import com.genc.smpps.model.ProductStatus;
import com.genc.smpps.model.dto.OrderProgressResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.genc.smpps.model.ProductionOrder;
import com.genc.smpps.repo.ProductionOrderRepository;
import com.genc.smpps.repo.ProductRepository;
import com.genc.smpps.repo.QualityInspectionRepository;

@Service
@Transactional
public class ProductionOrderService {

    private final ProductionOrderRepository repo;
    private final ProductRepository productRepository;
    private final QualityInspectionRepository qualityInspectionRepository;

    public ProductionOrderService(ProductionOrderRepository repo,
                                  ProductRepository productRepository,
                                  QualityInspectionRepository qualityInspectionRepository) {
        this.repo = repo;
        this.productRepository = productRepository;
        this.qualityInspectionRepository = qualityInspectionRepository;
    }


    public ProductionOrder createProductionOrder(ProductionOrder order) {
        FinishedProduct product = findProductOrThrow(order.getProductId());
        ensureProductCanBeOrdered(product);
        order.setProduct(product);
        // New orders always enter the workflow as PLANNED; status changes are handled only by transition methods.
        order.setOrderStatus(OrderStatus.PLANNED);
        normalizeProducedQuantity(order);
        ensureQuantityBounds(order);
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
        normalizeProducedQuantity(order);
        ensureQuantityBounds(order);
        if (order.getProducedQuantity() < order.getPlannedQuantity()) {
            throw new IllegalStateException("Produced quantity must meet planned quantity before completion");
        }
        if (!qualityInspectionRepository.existsByOrderOrderIdAndInspectionResult(order.getOrderId(), InspectionResult.PASS)) {
            throw new IllegalStateException("At least one passing quality inspection is required before completion");
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


    @Transactional(readOnly = true)
    public List<ProductionOrder> getAllOrders() {
        return repo.findAll();
    }


    @Transactional(readOnly = true)
    public OrderProgressResponse getOrderProgress(Integer id) {
        ProductionOrder order = findOrderOrThrow(id);
        int planned = order.getPlannedQuantity() != null ? order.getPlannedQuantity() : 0;
        int produced = order.getProducedQuantity() != null ? order.getProducedQuantity() : 0;
        double percentComplete = planned > 0 ? Math.min(100.0, (produced * 100.0) / planned) : 0.0;
        return new OrderProgressResponse(order.getOrderId(), order.getOrderStatus(), produced, planned, percentComplete);
    }


    @Transactional(readOnly = true)
    public long countOrders() {
        return repo.count();
    }


    public ProductionOrder findOrderByIdOrThrow(Integer id) {
        return findOrderOrThrow(id);
    }


    public ProductionOrder updateOrder(ProductionOrder updatedOrder) {

        ProductionOrder existing = findOrderOrThrow(updatedOrder.getOrderId());

        if (existing.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Completed orders cannot be updated");
        }
        if (existing.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled orders cannot be updated");
        }

        if (existing.getOrderStatus() == OrderStatus.RELEASED) {
            throw new IllegalStateException("Released orders cannot be updated; start or cancel the order instead");
        }
        if (existing.getOrderStatus() == OrderStatus.IN_PROGRESS && !isOnlyProducedQuantityChanging(existing, updatedOrder)) {
            throw new IllegalStateException("Only produced quantity can be updated for in-progress orders");
        }

        FinishedProduct product = findProductOrThrow(updatedOrder.getProductId());
        ensureProductCanBeOrdered(product);

        existing.setProduct(product);
        existing.setPlannedQuantity(updatedOrder.getPlannedQuantity());
        existing.setProducedQuantity(updatedOrder.getProducedQuantity());
        existing.setStartDate(updatedOrder.getStartDate());
        existing.setEndDate(updatedOrder.getEndDate());
        // Order status is intentionally ignored here; use release/start/complete/cancel endpoints for status changes.
        normalizeProducedQuantity(existing);
        ensureQuantityBounds(existing);

        return repo.save(existing);
    }


    private void normalizeProducedQuantity(ProductionOrder order) {
        if (order.getProducedQuantity() == null) {
            order.setProducedQuantity(0);
        }
    }

    private void ensureQuantityBounds(ProductionOrder order) {
        if (order.getPlannedQuantity() == null || order.getPlannedQuantity() <= 0) {
            throw new IllegalArgumentException("Planned quantity must be greater than 0");
        }
        if (order.getProducedQuantity() == null || order.getProducedQuantity() < 0) {
            throw new IllegalArgumentException("Produced quantity cannot be negative");
        }
        if (order.getProducedQuantity() > order.getPlannedQuantity()) {
            throw new IllegalArgumentException("Produced quantity cannot exceed planned quantity");
        }
    }

    private boolean isOnlyProducedQuantityChanging(ProductionOrder existing, ProductionOrder updatedOrder) {
        return Objects.equals(existing.getProductId(), updatedOrder.getProductId())
                && Objects.equals(existing.getPlannedQuantity(), updatedOrder.getPlannedQuantity())
                && Objects.equals(existing.getStartDate(), updatedOrder.getStartDate())
                && Objects.equals(existing.getEndDate(), updatedOrder.getEndDate());
    }

    private void ensureProductCanBeOrdered(FinishedProduct product) {
        if (product.getProductStatus() != ProductStatus.ACTIVE) {
            throw new IllegalStateException("Only ACTIVE products can be used in production orders");
        }
    }



    private ProductionOrder findOrderOrThrow(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than 0");
        }
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }


    private FinishedProduct findProductOrThrow(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than 0");
        }
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
    }

}