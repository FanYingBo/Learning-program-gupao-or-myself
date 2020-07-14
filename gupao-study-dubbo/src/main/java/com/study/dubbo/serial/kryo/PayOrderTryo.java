package com.study.dubbo.serial.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.study.dubbo.app.service.PayOrder;

public class PayOrderTryo extends Serializer<PayOrder> {
    @Override
    public void write(Kryo kryo, Output output, PayOrder object) {

    }

    @Override
    public PayOrder read(Kryo kryo, Input input, Class<PayOrder> type) {
        return null;
    }
}
