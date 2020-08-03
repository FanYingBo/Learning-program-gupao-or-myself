package com.study.dubbo.api.impl;

import com.study.dubbo.IPayService;
import com.study.dubbo.mode.PayOrder;
import com.study.dubbo.mode.PayOrderContext;
import com.study.dubbo.mode.PayType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("alipayService")
public class AlipayService implements IPayService {
    @Value("${test.dubbo.server.id}")
    private String serverId;
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
                .serverId(Integer.parseInt(serverId))
                .build();
        return new PayOrderContext(payOrder);
    }

    @Override
    public void publishPayOrderEvent(PayOrderContext payOrderContext) {

    }
}
