package com.study.gupao.designmode.proxy.dynamic.jdk;


import com.study.gupao.designmode.proxy.dynamic.jdk.child.ProxyedClass;
import com.study.gupao.designmode.proxy.dynamic.jdk.parent.DynamicProxyJdkOne;
import com.study.gupao.designmode.proxy.dynamic.jdk.parent.DynamicProxyJdkTwo;
import com.study.gupao.designmode.proxy.dynamic.jdk.service.ProxyService;

/**
 * jdk 动态代理需要实现某个接口
 */
public class JdkProxyDemo {

    public static void main(String[] args) throws Exception {
        ProxyService instance = (ProxyService) new DynamicProxyJdkTwo().getInstance(new ProxyedClass());
        instance.findLove();
        instance.shopping();
    }
}
