package com.study.gupao.designmode.proxy.dynamic.jdk.parent;

import com.study.gupao.designmode.proxy.dynamic.jdk.service.ProxyService;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Dynamic proxy
 */
public class DynamicProxyJdkOne implements InvocationHandler{

    public ProxyService proxyService;

    public Object getInstance(ProxyService proxyService) throws Exception {
        this.proxyService = proxyService;
        Class<?> clazz = proxyService.getClass();

        // 用来生成一个新的对象（字节码重组来实现）
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        byte[] binary = ProxyGenerator.generateProxyClass("$Proxy0",new Class<?>[]{ProxyService.class});
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\$Proxy0.class"));
        fileOutputStream.write(binary);
        return obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是代理1：我要帮你实现你所继承接口的方法，并响应你方法的需求");
        System.out.println("开始调用方法"+method.getName());
        method.invoke(this.proxyService,args);
        return null;
    }
}
