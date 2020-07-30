package com.study.dubbo.mode;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class PayOrderContext implements Serializable {

    private AtomicInteger status = new AtomicInteger(0);

    private PayOrder payOrder;

    public PayOrderContext(PayOrder payOrder) {
        this.payOrder = payOrder;
    }

    public int getStatus() {
        return status.get();
    }

    public boolean setStatus(int expect, int status) {
        return this.status.compareAndSet(expect,status);
    }

    public PayOrder getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(PayOrder payOrder) {
        this.payOrder = payOrder;
    }

    @Override
    public String toString() {
        return "PayOrderContext{" +
                "status=" + status.get() +
                ", payOrder=" + payOrder +
                '}';
    }
}
