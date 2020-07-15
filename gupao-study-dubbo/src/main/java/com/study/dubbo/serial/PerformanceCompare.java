package com.study.dubbo.serial;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.study.dubbo.app.service.PayOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * 20000次的序列化
 * JDK serialize 耗时：160
 * hessian serialize 耗时：1877
 * hessian2 serialize 耗时：1365
 * kryo serialize 耗时：75
 *
 */
public class PerformanceCompare {
    public static List<byte[]> serializeList = new ArrayList<>();
    public static List<byte[]> serializeList_Hessian = new ArrayList<>();
    public static List<byte[]> serializeList_kryo = new ArrayList<>();
    public static List<byte[]> serializeList_Hessian2 = new ArrayList<>();
    private static final Kryo kryo = new Kryo();
    private static final Output output = new Output();
    private static final Input input = new Input();
    private static int count = 10000;
    public static void main(String[] args) {
        testSerialize();
        testDeserialize();
    }

    private static void testSerialize(){
        PayOrder payOrder = SerializableObjectFactory.getPayOrder();

        StartTime startTime = new StartTime("JDK serialize");
        startTime.start();
        for(int i = 0;i < count;i++){
            byte[] bytes = SerializeUtils.jdkSerialize(payOrder);
            serializeList.add(bytes);
        }
        startTime.stop();
        startTime.setDesc("hessian serialize").start();
        for(int i = 0;i < count;i++){
            byte[] bytes = SerializeUtils.hessianSerialize(payOrder);
            serializeList_Hessian.add(bytes);
        }
        startTime.stop();
        startTime.setDesc("hessian2 serialize").start();
        for(int i = 0;i < count;i++){
            byte[] bytes = SerializeUtils.hessian2Serialize(payOrder);
            serializeList_Hessian2.add(bytes);
        }
        startTime.stop();
        startTime.setDesc("kryo serialize").start();

        kryo.setReferences(true);
        output.setBuffer(new byte[1024]);
        for(int i = 0;i < count;i++){
            byte[] bytes = SerializeUtils.kryoSerialize(kryo, output, payOrder);
            serializeList_kryo.add(bytes);
        }
        startTime.stop();
    }

    private static void testDeserialize(){
        StartTime startTime = new StartTime("JDK deserialize");
        startTime.start();
        for(int i = 0;i < count;i++){
            SerializeUtils.jdkDeserialize(serializeList.get(i),PayOrder.class);
        }
        startTime.stop();
        startTime.setDesc("hessian deserialize").start();
        for(int i = 0;i < count;i++){
            SerializeUtils.hessianDeserialize(serializeList_Hessian.get(i),PayOrder.class);
        }
        startTime.stop();
        startTime.setDesc("hessian2 deserialize").start();
        for(int i = 0;i < count;i++){
            SerializeUtils.hessian2Deserialize(serializeList_Hessian2.get(i),PayOrder.class);
        }
        startTime.stop();
        startTime.setDesc("kryo deserialize").start();

        kryo.setReferences(true);

        for(int i = 0;i < count;i++){
            input.setBuffer(serializeList_kryo.get(i));
            SerializeUtils.kryoDeSerialize(kryo,input,PayOrder.class);
        }
        startTime.stop();
    }
}
