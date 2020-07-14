package com.study.dubbo.app.service;

import java.io.Serializable;

public class PayOrder implements Serializable {

    private String productId;

    private String productDesc;

    private char currencyCode;

    private float money;

    private int payType;

    public PayOrder(){
    }

    public PayOrder(String productId, String productDesc, char currencyCode, float money, int payType) {
        this.productId = productId;
        this.productDesc = productDesc;
        this.currencyCode = currencyCode;
        this.money = money;
        this.payType = payType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public char getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(char currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public static class Builder{
        private String productId;
        private String productDesc;
        private char currencyCode;
        private float money;
        private int  payType;

        private static final Builder DEFAULT_INSTANCE = new Builder();
        public static Builder getDefaultInstance(){
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder toBuilder(){
            this.money = 0;
            this.payType = 0;
            this.productId = null;
            this.productDesc = null;
            return this;
        }

        public Builder productId(String productId){
            this.productId = productId;
            return this;
        }
        public Builder productDesc(String productDesc){
            this.productDesc = productDesc;
            return this;
        }
        public Builder currencyCode(char currencyCode){
            this.currencyCode = currencyCode;
            return this;
        }
        public Builder money(float money){
            this.money = money;
            return this;
        }
        public Builder payType(int payType){
            this.payType = payType;
            return this;
        }
        public PayOrder build(){
            PayOrder payOrder = new PayOrder();
            payOrder.productId = productId;
            payOrder.money = money;
            payOrder.productDesc = productDesc;
            payOrder.currencyCode = currencyCode;
            payOrder.payType = payType;
            return payOrder;
        }
    }

    @Override
    public String toString() {
        return "PayOrder{" +
                "productId='" + productId + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", currencyCode=" + currencyCode +
                ", money=" + money +
                ", payType=" + payType +
                '}';
    }
}
