package com.company.model;

import java.util.Date;

public class Product {
    private String productId, name, supplierId;
    private int stockAvailable, openingStock;
    private Date lastSupplyDate;
    private double unitPrice;

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public int getStockAvailable() { return stockAvailable; }
    public void setStockAvailable(int stockAvailable) { this.stockAvailable = stockAvailable; }

    public int getOpeningStock() { return openingStock; }
    public void setOpeningStock(int openingStock) { this.openingStock = openingStock; }

    public Date getLastSupplyDate() { return lastSupplyDate; }
    public void setLastSupplyDate(Date lastSupplyDate) { this.lastSupplyDate = lastSupplyDate; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}
