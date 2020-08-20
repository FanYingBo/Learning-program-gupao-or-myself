package com.study.dubbo.serial;

import com.study.dubbo.app.service.PayOrder;

public class SerializableObjectFactory {

    public static PayOrder getPayOrder(){
        PayOrder payOrder = PayOrder.Builder.getDefaultInstance().toBuilder()
                .productDesc("新手礼包")
                .productId("newGift")
                .payType(1)
                .currencyCode('$')
                .money(1.99f)
                .build();
        return payOrder;
    }
}
