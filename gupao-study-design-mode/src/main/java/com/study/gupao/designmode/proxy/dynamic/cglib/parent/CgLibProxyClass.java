package com.study.gupao.designmode.proxy.dynamic.cglib.parent;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CgLibProxyClass implements MethodInterceptor{

    public Object getInstance(Class<?>  clazz){
        Enhancer enhancer = new Enhancer();
        //
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 业务的增强
        System.out.println("我是代理：我要帮你实现你所继承接口的方法，并响应你方法的需求");
        System.out.println("开始调用方法"+method.getName());
        methodProxy.invokeSuper(o,objects);
        return null;
    }
}
