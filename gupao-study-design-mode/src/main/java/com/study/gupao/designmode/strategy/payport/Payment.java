package com.study.gupao.designmode.strategy.payport;

/**
 * 支付渠道
 */
public interface Payment {

    public PayState pay(String uid,double amount);
}
