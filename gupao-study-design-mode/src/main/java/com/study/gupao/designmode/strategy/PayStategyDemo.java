package com.study.gupao.designmode.strategy;

import com.study.gupao.designmode.strategy.enumeration.PayType;
import com.study.gupao.designmode.strategy.order.Order;

public class PayStategyDemo {

    public static void main(String[] args) {
        Order order = new Order("SEERAFA2231122","018062700115",23.78);
        System.out.println(order.pay(PayType.WECHAT_PAY));
    }
}
