package com.study.dubbo.app.service;

public class WeChatPayService implements IPayService{

    @Override
    public PayType getPayType() {
        return PayType.WECHART;
    }

    @Override
    public PayOrderContext placeAnOrder(String productId,String productDesc,char currencyCode, float money) {
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
