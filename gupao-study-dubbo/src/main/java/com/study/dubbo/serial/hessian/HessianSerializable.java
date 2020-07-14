package com.study.dubbo.serial.hessian;

import com.study.dubbo.app.service.PayOrder;
import com.study.dubbo.serial.SerializableObjectFactory;
import com.study.dubbo.serial.SerializeUtils;

import java.io.Serializable;

/**
 * hessian 序列化
 */
public class HessianSerializable {

    public static void main(String[] args) {
        PayOrder payOrder = SerializableObjectFactory.getPayOrder();
        byte[] ser = serialize(payOrder);
        System.out.println("序列化成功");
        PayOrder deserialize = deserialize(ser, PayOrder.class);
        System.out.println("反序列化成功 "+ deserialize);
    }

    public static byte[] serialize(Serializable serializable){
        return SerializeUtils.hessianSerialize(serializable);
    }

    public static <T> T deserialize(byte[] bytes,Class<T> tClass){
        return SerializeUtils.hessianDeserialize(bytes,tClass);
    }
}
