package com.study.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * 这里遇到一个问题，为什么无法加载到 IPayService？报错了
 */
public class DubboSpiDemo {

    public static void main(String[] args) {
        ExtensionLoader<IPayService> extensionLoader = ExtensionLoader.getExtensionLoader(IPayService.class);
        IPayService aliPay = extensionLoader.getExtension("aliPay");
        aliPay.payOrder("221212",32,"test");
    }
}
