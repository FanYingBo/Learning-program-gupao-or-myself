package com.study.gupao.designmode.proxy.dynamic.cglib;

import com.study.gupao.designmode.proxy.dynamic.cglib.child.CgLibProxedClass;
import com.study.gupao.designmode.proxy.dynamic.cglib.parent.CgLibProxyClass;
import com.study.gupao.designmode.proxy.dynamic.cglib.supper.CgLibProxyService;

/**
 * cglib
 * 动态代理可以实现某个具体的类的代理
 */
public class CglibProxyDemo {

    public static void main(String[] args) {
        CgLibProxedClass instance = (CgLibProxedClass)new CgLibProxyClass().getInstance(CgLibProxedClass.class);
        instance.findLove();
        System.out.println(instance.getClass());
    }
}
