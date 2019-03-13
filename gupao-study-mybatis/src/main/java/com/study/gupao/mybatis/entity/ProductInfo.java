package com.study.gupao.mybatis.entity;

public class ProductInfo {

    private String productCode;

    private String description;

    private int saleType;

    private int supplyId;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", saleType=" + saleType +
                ", supplyId=" + supplyId +
                '}';
    }
}
