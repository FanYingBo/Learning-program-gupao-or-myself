package com.study.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class DubboSpiDemo {

    public static void main(String[] args) {
        ExtensionLoader<IPayService> extensionLoader = ExtensionLoader.getExtensionLoader(IPayService.class);
        IPayService aliPay = extensionLoader.getExtension("aliPay");
        aliPay.payOrder("221212",32,"test");
    }
}
