package com.study.selfs.tcc.mode;

import java.util.concurrent.atomic.AtomicLong;

public class Product {

    private String productCode;

    private AtomicLong totalStock;

    private AtomicLong freeStock;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getTotalStock() {
        return totalStock.get();
    }

    public void setTotalStock(long totalStock) {
        this.totalStock = new AtomicLong(totalStock);
    }

    public long getFreeStock() {
        return freeStock.get();
    }

    public void setFreeStock(long freeStock) {
        this.freeStock = new AtomicLong(freeStock);
    }
}
