package com.study.gupao.designmode.proxy.dynamic.custom.parent;

import com.study.gupao.designmode.proxy.dynamic.custom.MyClassLoader;
import com.study.gupao.designmode.proxy.dynamic.custom.MyInvocationHandler;
import com.study.gupao.designmode.proxy.dynamic.custom.MyProxy;
import com.study.gupao.designmode.proxy.dynamic.jdk.service.ProxyService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MyProxyClass implements MyInvocationHandler{

    private ProxyService proxyService;

    public Object getInstance(ProxyService proxyService){
        this.proxyService = proxyService;
        return MyProxy.newProxyInstance(new MyClassLoader(),proxyService.getClass().getInterfaces(),this);
    }


    public Object invoke(Object proxy,Method method,Object[] objects) throws Throwable{
            System.out.println("我是代理1：我要帮你实现你所继承接口的方法，并响应你方法的需求");
            System.out.println("开始调用方法"+method.getName());
            method.invoke(this.proxyService,objects);
        return null;
    }
}
