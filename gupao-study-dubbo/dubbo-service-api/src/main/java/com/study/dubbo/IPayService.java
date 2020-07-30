package com.study.dubbo;

import com.study.dubbo.mode.PayOrderContext;
import com.study.dubbo.mode.PayType;

public interface IPayService {

    PayType getPayType();

    PayOrderContext placeAnOrder(String productId, String productDesc, char currencyCode, float money);

    void publishPayOrderEvent(PayOrderContext payOrderContext);
}
