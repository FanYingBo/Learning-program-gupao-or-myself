package com.study.dubbo.api.impl;

import com.study.dubbo.IPayService;
import com.study.dubbo.mode.PayOrder;
import com.study.dubbo.mode.PayOrderContext;
import com.study.dubbo.mode.PayType;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@DubboService(interfaceClass = IPayService.class ,group = "alipayService")
@Service
public class WeChatPayService implements IPayService {
    @Value("${test.dubbo.server.id}")
    private String serverId;
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
                .serverId(Integer.parseInt(serverId))
                .build();
        // 请求 微信支付接口
        return new PayOrderContext(payOrder);
    }

    @Override
    public void publishPayOrderEvent(PayOrderContext payOrderContext) {

    }


}
