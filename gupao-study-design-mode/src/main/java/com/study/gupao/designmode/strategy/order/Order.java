package com.study.gupao.designmode.strategy.order;

import com.study.gupao.designmode.strategy.enumeration.PayType;
import com.study.gupao.designmode.strategy.payport.PayState;
import com.study.gupao.designmode.strategy.payport.Payment;

public class Order {

    private String uid;

    private String orderId;

    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PayState pay(PayType payType){
         return payType.get().pay(this.uid,this.amount);
    }
}
