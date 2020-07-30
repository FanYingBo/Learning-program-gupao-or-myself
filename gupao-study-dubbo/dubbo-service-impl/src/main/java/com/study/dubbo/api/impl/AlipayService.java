package com.study.dubbo.api.impl;

import com.study.dubbo.IPayService;
import com.study.dubbo.mode.PayOrder;
import com.study.dubbo.mode.PayOrderContext;
import com.study.dubbo.mode.PayType;
import org.springframework.stereotype.Service;

@Service("alipayService")
public class AlipayService implements IPayService {
    @Override
    public PayType getPayType() {
        return PayType.ALIPAY;
    }

    @Override
    public PayOrderContext placeAnOrder(String productId, String productDesc, char currencyCode, float money) {
        PayOrder payOrder = PayOrder.Builder.newBuilder()
                .currencyCode(currencyCode)
                .money(money)
                .payType(getPayType().getType())
                .productId(productId)
                .productDesc(productDesc)
                .build();
        return new PayOrderContext(payOrder);
    }

    @Override
    public void publishPayOrderEvent(PayOrderContext payOrderContext) {

    }
}
