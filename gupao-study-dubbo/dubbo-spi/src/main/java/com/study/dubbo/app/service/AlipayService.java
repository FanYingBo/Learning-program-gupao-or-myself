package com.study.dubbo.app.service;

public class AlipayService implements IPayService{
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
