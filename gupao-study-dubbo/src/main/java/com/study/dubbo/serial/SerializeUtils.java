package com.study.dubbo.serial;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.HessianInput;
import com.alibaba.com.caucho.hessian.io.HessianOutput;

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

}
