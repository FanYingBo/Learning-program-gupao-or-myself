package com.study.spi;

import java.util.ServiceLoader;

/**
 *  java spi
 *  META-INF/service 定义接口的实现
 */
public class JavaSpiDemo {

    public static void main(String[] args) {
        ServiceLoader<IPayService> iPayServices = ServiceLoader.load(IPayService.class);
        iPayServices.forEach(iPayService -> iPayService.payOrder("3122221",22,"test"));
    }
}
