package com.study.dubbo.api.impl;

import com.study.dubbo.IPayService;
import com.study.dubbo.mode.PayOrder;
import com.study.dubbo.mode.PayOrderContext;
import com.study.dubbo.mode.PayType;
import org.springframework.stereotype.Service;

@Service("weChatPayService")
public class WeChatPayService implements IPayService {

    @Override
    public PayType getPayType() {
        return PayType.WECHART;
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
        // 请求 微信支付接口
        return new PayOrderContext(payOrder);
    }

    @Override
    public void publishPayOrderEvent(PayOrderContext payOrderContext) {

    }


}
