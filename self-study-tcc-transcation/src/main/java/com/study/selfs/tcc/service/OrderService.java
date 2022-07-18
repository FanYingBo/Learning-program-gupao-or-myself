package com.study.selfs.tcc.service;

import com.study.selfs.tcc.mode.PayOrder;

public class OrderService{
    /**
     * 缓存保存订单信息 未支付的订单
     * @param payOrder
     * @return
     */
    public int saveOrderCache(PayOrder payOrder){
        return 1;
    }

    /**
     *
     * @param status
     */
    public PayOrder updateOrderStatus(PayOrder payOrder, int status){
        return payOrder;
    }

    public PayOrder getPayOrder(String orderNum){
        return new PayOrder();
    }

}
