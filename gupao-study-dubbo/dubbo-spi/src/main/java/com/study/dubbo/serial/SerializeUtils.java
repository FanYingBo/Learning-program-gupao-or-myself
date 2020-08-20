package com.study.dubbo.serial;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.HessianInput;
import com.alibaba.com.caucho.hessian.io.HessianOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;

/**
 * 序列化方式
 */
public class SerializeUtils {

    public static byte[] hessianSerialize(Serializable serializable){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(outputStream);
        try {
            hessianOutput.writeObject(serializable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
    public static <T> T hessianDeserialize(byte[] bytes,Class<T> tClass){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Object object = null;
        try {
            HessianInput objectInputStream = new HessianInput(bais);
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tClass.cast(object);
    }

    public static byte[] hessian2Serialize(Serializable serializable){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output hessianOutput = new Hessian2Output(outputStream);
        try {
            hessianOutput.writeObject(serializable);
            hessianOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static <T> T hessian2Deserialize(byte[] bytes,Class<T> tClass){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Object object = null;
        try {
            Hessian2Input objectInputStream = new Hessian2Input(bais);
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tClass.cast(object);
    }

    public static byte[] jdkSerialize(Serializable serializable){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(serializable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static <T> T jdkDeserialize(byte[] bytes,Class<T> tClass){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tClass.cast(object);
    }

    /**
     * 这样性能最高
     * @param kryo
     * @param output
     * @param serializable
     * @return
     */
    public static byte[] kryoSerialize(Kryo kryo, Output output, Serializable serializable){
        if(kryo != null && output != null){
            output.clear();
            kryo.writeObject(output,serializable);
            return output.toBytes();
        }
        return null;
    }

    public static byte[] kryoSerialize(Kryo kryo, byte[] bytes,Serializable serializable){
        Output output = new Output();
        output.setBuffer(bytes);
        kryo.writeObject(output,serializable);
        return bytes;
    }

    public static byte[] kryoSerialize(Serializable serializable){
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        Kryo kryo = new Kryo();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeObject(output,serializable);
        output.flush();
        return byteArrayOutputStream.toByteArray();
    }


    public static <T> T kryoDeSerialize(Kryo kryo,Input input,Class<T> tClass){
        if(kryo != null && input != null){
            return kryo.readObjectOrNull(input, tClass);
        }
        return null;
    }

    public static <T> T kryoDeSerialize(Kryo kryo,byte[] bytes,Class<T> tClass){
        Input input = new Input();
        input.setBuffer(bytes);
        return kryo.readObjectOrNull(input, tClass);
    }

    public static <T> T kryoDeSerialize(byte[] bytes,Class<T> tClass){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Kryo kryo = new Kryo();
        Input input = new Input(byteArrayInputStream);
        return kryo.readObjectOrNull(input, tClass);
    }
}
