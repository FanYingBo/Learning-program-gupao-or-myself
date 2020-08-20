package com.study.dubbo.serial.java;

import com.study.dubbo.app.service.PayOrder;
import com.study.dubbo.serial.SerializableObjectFactory;
import com.study.dubbo.serial.SerializeUtils;

import java.io.*;

/**
 * jdk自带的序列化实现
 */
public class JDKSerializableDemo {

    public static void main(String[] args) {
        PayOrder payOrder = SerializableObjectFactory.getPayOrder();
        byte[] ser = serialize(payOrder);
        System.out.println("序列化成功 "+ser.length);
        PayOrder deserialize = deserialize(ser, PayOrder.class);
        System.out.println("反序列化成功 "+ deserialize);
    }

    public static byte[] serialize(Serializable serializable){
        return SerializeUtils.jdkSerialize(serializable);
    }

    public static <T> T deserialize(byte[] bytes,Class<T> tClass){
        Object object = SerializeUtils.jdkDeserialize(bytes,tClass);
        return tClass.cast(object);
    }

}
