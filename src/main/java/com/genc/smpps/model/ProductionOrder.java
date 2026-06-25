package com.genc.smpps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class ProductionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @NotNull(message = "Product is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private FinishedProduct product;

    @NotNull(message = "Planned quantity is required")
    @Positive(message = "Planned quantity must be greater than 0")
    private Integer plannedQuantity;

    @NotNull(message = "Produced quantity is required")
    @PositiveOrZero(message = "Produced quantity cannot be negative")
    private Integer producedQuantity = 0;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull(message = "Order status is required")
    @Enumerated(EnumType.STRING)
    public enum OrderStatus {
        PLANNED,
        RELEASED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }


    @AssertTrue(message = "Product ID must be greater than 0")
    public boolean isProductReferenceValid() {
        return getProductId() != null && getProductId() > 0;
    }

    @AssertTrue(message = "Produced quantity cannot be greater than planned quantity")
    public boolean isProducedQuantityValid() {
        if (plannedQuantity == null || producedQuantity == null) {
            return true;
        }
        return producedQuantity <= plannedQuantity;
    }

    @AssertTrue(message = "End date cannot be before start date")
    public boolean isDateRangeValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }


    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public FinishedProduct getProduct() { return product; }
    public void setProduct(FinishedProduct product) { this.product = product; }

    public Integer getProductId() { return product != null ? product.getProductId() : null; }
    public void setProductId(Integer productId) {
        if (productId == null) {
            this.product = null;
            return;
        }
        FinishedProduct p = new FinishedProduct();
        p.setProductId(productId);
        this.product = p;
    }

    public Integer getPlannedQuantity() { return plannedQuantity; }
    public void setPlannedQuantity(Integer plannedQuantity) { this.plannedQuantity = plannedQuantity; }

    public Integer getProducedQuantity() { return producedQuantity; }
    public void setProducedQuantity(Integer producedQuantity) { this.producedQuantity = producedQuantity; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
}