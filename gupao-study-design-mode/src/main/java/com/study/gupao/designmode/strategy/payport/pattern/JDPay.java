package com.study.gupao.designmode.strategy.payport.pattern;

import com.study.gupao.designmode.strategy.payport.PayState;
import com.study.gupao.designmode.strategy.payport.Payment;

/**
 * 不同的支付渠道
 */
public class JDPay implements Payment{
    @Override
    public PayState pay(String uid, double amount) {
        System.out.println("欢迎使用京东支付！");
        System.out.println("正在支付...");
        return new PayState(200,"Pay OK!","支付单号："+uid+",支付金额："+amount);
    }
}
