package com.study.gupao.mybatis.entity;

import java.util.Date;

public class CustomOrder {

    private String orderCode;

    private String productCode;

    private String name;

    private Date createDate;

    private int isReceive;

    private ProductInfo productInfo;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(int isReceive) {
        this.isReceive = isReceive;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public String toString() {
        return "CustomOrder{" +
                "orderCode='" + orderCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", isReceive=" + isReceive +
                ", productInfo=" + productInfo +
                '}';
    }
}
