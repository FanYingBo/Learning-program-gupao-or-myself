package com.study.gupao.designmode.strategy.enumeration;

import com.study.gupao.designmode.strategy.payport.PayState;
import com.study.gupao.designmode.strategy.payport.Payment;
import com.study.gupao.designmode.strategy.payport.pattern.AliPay;
import com.study.gupao.designmode.strategy.payport.pattern.JDPay;
import com.study.gupao.designmode.strategy.payport.pattern.WeChatPay;

public enum PayType {
    ALI_PAY(new AliPay()),
    JD_PAY(new JDPay()),
    WECHAT_PAY(new WeChatPay());


    private Payment payment;

    PayType(Payment payment){
        this.payment = payment;
    }

    public Payment get(){
        return this.payment;
    }
}
