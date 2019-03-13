package com.study.gupao.designmode.proxy.dynamic.cglib.child;


import com.study.gupao.designmode.proxy.dynamic.cglib.supper.CgLibProxyService;

public class CgLibProxedClass extends CgLibProxyService{


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
