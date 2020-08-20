package com.study.dubbo.app.service;

public interface IPayService {

    PayType getPayType();

    PayOrderContext placeAnOrder(String productId,String productDesc,char currencyCode, float money);

    void publishPayOrderEvent(PayOrderContext payOrderContext);
}
