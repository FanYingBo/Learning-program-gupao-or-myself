package com.study.dubbo.serial.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.study.dubbo.app.service.PayOrder;
import com.study.dubbo.serial.SerializableObjectFactory;
import com.study.dubbo.serial.SerializeUtils;

import java.util.ConcurrentModificationException;

/**
 * Kryo 是线程不安全的 并发情况下会抛出 {@link ConcurrentModificationException}
 * 可以使用ThreadLocal 保存kryo 对象实例，output ,input 实例
 * {@link https://www.jianshu.com/p/f56c9360936d}
 */
public class KryoSerializeDemo {

    public static void main(String[] args) {
//        byte[] bytes = SerializeUtils.kryoSerialize(SerializableObjectFactory.getPayOrder());
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        Output output = new Output();
        output.setBuffer(new byte[1024]);
        PayOrder payOrder1 = SerializableObjectFactory.getPayOrder();
        byte[] bytes = SerializeUtils.kryoSerialize(kryo, output, payOrder1);
        System.out.println("序列化成功 "+bytes.length);
        Input input = new Input();
        input.setBuffer(bytes);
        PayOrder payOrder = SerializeUtils.kryoDeSerialize(kryo,input, PayOrder.class);
        System.out.println("反序列化成功 "+payOrder);
    }

}
