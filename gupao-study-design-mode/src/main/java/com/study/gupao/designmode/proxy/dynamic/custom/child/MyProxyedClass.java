package com.study.gupao.designmode.proxy.dynamic.custom.child;

import com.study.gupao.designmode.proxy.dynamic.custom.MyInvocationHandler;
import com.study.gupao.designmode.proxy.dynamic.jdk.service.ProxyService;

public class MyProxyedClass implements ProxyService{


    public void findLove(){
        System.out.println("被代理的ProxyedClass找到对象A");
    }

    public void findJob() {
        System.out.println("被代理的ProxyedClass找到工作B");
    }

    public void shopping() {
        System.out.println("被代理的ProxyedClass购物C");
    }
}
