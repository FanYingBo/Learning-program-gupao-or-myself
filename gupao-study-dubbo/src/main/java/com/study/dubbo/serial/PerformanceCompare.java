package com.study.dubbo.serial;

import com.study.dubbo.app.service.PayOrder;

public class PerformanceCompare {

    public static void main(String[] args) {
        PayOrder payOrder = SerializableObjectFactory.getPayOrder();
        int count = 20000;
        StartTime startTime = new StartTime("JDK serialize");
        startTime.start();
        for(int i = 0;i < count;i++){
            SerializeUtils.jdkSerialize(payOrder);
        }
        startTime.stop();
        startTime.setDesc("hessian serialize").start();
        for(int i = 0;i < count;i++){
            SerializeUtils.hessianSerialize(payOrder);
        }
        startTime.stop();
        startTime.setDesc("hessian2 serialize").start();
        for(int i = 0;i < count;i++){
            SerializeUtils.hessian2Serialize(payOrder);
        }
        startTime.stop();
    }
}
