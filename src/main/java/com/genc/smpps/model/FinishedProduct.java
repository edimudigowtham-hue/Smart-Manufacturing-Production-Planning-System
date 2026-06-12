package com.genc.smpps.model;

import jakarta.persistence.*;

@Entity
public class FinishedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productCode;
    private String productName;
    private String bomVersion;
    private double standardCost;

    //changed to enum
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus; // ACTIVE, INACTIVE, PHASED_OUT


    // Getters and Setters
    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getBomVersion() { return bomVersion; }
    public void setBomVersion(String bomVersion) { this.bomVersion = bomVersion; }

    public double getStandardCost() { return standardCost; }
    public void setStandardCost(double standardCost) { this.standardCost = standardCost; }

//    public String getProductStatus() { return productStatus; }
//    public void setProductStatus(String productStatus) { this.productStatus = productStatus; }
}